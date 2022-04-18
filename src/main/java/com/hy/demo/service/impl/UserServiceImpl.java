package com.hy.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.common.GlobalConstant;
import com.hy.demo.entity.*;
import com.hy.demo.mapper.RoleMapper;

import com.hy.demo.mapper.UserMapper;
import com.hy.demo.service.IMenuService;
import com.hy.demo.service.IRoleMenuService;

import com.hy.demo.service.UserService;
import com.hy.demo.util.RedisUtils;
import com.hy.demo.util.Result;
import com.hy.demo.util.TokenUtils;
import com.hy.demo.vo.UserDto;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    private static final Logger log=LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Result login(UserDto userDto) {
        //判断空
        String uname = userDto.getUname();
        String upassword = userDto.getUpassword();
        if(StringUtils.isBlank(uname) || StringUtils.isBlank(upassword)){
            return Result.error("300","参数错误");
        }
        //获取用户信息
        User one = getControllerInfo(userDto);
        if(!Objects.equals(one,null)){
            BeanUtil.copyProperties(one,userDto,true);
            String token = TokenUtils.genToken(one.getUid().toString(), one.getUpassword());
            //存入到redis中 2022-04-15
            //redisUtils.set(GlobalConstant.REDIS_KEY_TOKEN+one.getUid(),token);
            redisUtils.set(GlobalConstant.REDIS_KEY_TOKEN+one.getUid(),token,GlobalConstant.REDIS_KEY_TOKEN_TIME, TimeUnit.HOURS);
            userDto.setToken(token);
            //设置动态菜单
            String role = one.getRole();
            List<Menu> menuList=getMenuListByRole(role);

            userDto.setMenuList(menuList);
            return Result.success(userDto);
        }
        return Result.error("300","用户名或者密码错误");

    }

    private List<Menu> getMenuListByRole(String roleFlag) {
        //唯一标识
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("flag",roleFlag);
        Role role = roleMapper.selectOne(roleQueryWrapper);
        //得到当前roleId,查找对应的菜单
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",role.getId());
        //集合菜单
        List<RoleMenu> list = iRoleMenuService.list(roleMenuQueryWrapper);
        List<Integer> menuIds = new ArrayList<>();
        list.forEach(l->{
                menuIds.add(l.getMenuId());
        });
        //查询全部的菜单栏对应的子菜单栏
        Result result = iMenuService.selectMenuList("");
       List<Menu> menuList= (List<Menu>) result.getData();
       //筛选的菜单
        List<Menu> menus=new ArrayList<>();

       for(Menu menu:menuList){
           if(menuIds.contains(menu.getId())){
               menus.add(menu);
           }
           List<Menu> children = menu.getChildren();
           //清除菜单ID不在找的里面
           children.removeIf(child->!menuIds.contains(child.getId()));
       }
        return menus;
    }

    @Override
    public Result register(UserDto userDto) {
        String cname = userDto.getUname();
        String cpassword = userDto.getUpassword().trim();
        String confirmPassword=userDto.getConfirmPassword().trim();
        if(StringUtils.isBlank(cname) || StringUtils.isBlank(cpassword) || StringUtils.isBlank(confirmPassword)){
            return Result.error("300","参数错误");
        }
        if(!cpassword.equals(confirmPassword)){
            return Result.error("300","输入的两次密码不一致");
        }
        User controllerInfo = getControllerInfo(userDto);
        if(controllerInfo==null){
            controllerInfo=new User();
            //BeanUtil.copyProperties(one,controllerDto,true);
            controllerInfo.setUname(cname);
            controllerInfo.setUpassword(cpassword);
            save(controllerInfo);
            return Result.success(controllerInfo);
        }else{
            return Result.error("300","用户信息已存在");
        }

    }

    @Override
    public Result updatePassword(UserDto userDto) {
        String upassword = userDto.getUpassword().trim();
        String confirmPassword=userDto.getConfirmPassword().trim();
        String newPassword = userDto.getNewPassword();
        Integer cid = userDto.getUid();
        if(cid==null || StringUtils.isBlank(upassword) || StringUtils.isBlank(confirmPassword) || StringUtils.isBlank(newPassword)){
            return Result.error("300","参数错误");
        }
        if(!newPassword.equals(confirmPassword)){
            return Result.error("300","输入的两次密码不一致");
        }
        int i = userMapper.updatePassword(newPassword, cid, upassword);
        if (i>0){
            //删除Redis中token值
            redisUtils.del(GlobalConstant.REDIS_KEY_TOKEN+cid);
            return Result.success();
        }
        return Result.error("300","操作失败");

    }

    @Override
    public Result userImportExcel(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook sheets = new XSSFWorkbook(inputStream);
            //获取表单sheet 第一个
            XSSFSheet sheetAt = sheets.getSheetAt(0);
            //获取第一行
            int firstRowNum = sheetAt.getFirstRowNum();
            //最后一行
            int lastRowNum = sheetAt.getLastRowNum();
            //存入数据集合
            List<User> users=new ArrayList<>();
            //遍历数据
            for(int i=firstRowNum+1;i<lastRowNum+1;i++){
                XSSFRow row = sheetAt.getRow(i);
                if(row!=null){
                   /* //获取第一行的第一列
                    int firstCellNum = row.getFirstCellNum();
                    //获取第一行的最后列
                    short lastCellNum = row.getLastCellNum();
                    for (int j=firstCellNum;j<lastCellNum+1;j++){
                        //放入集合中需要可以用这种方法
                        String cellValue = getValue(row.getCell(firstCellNum));
                    }*/
                    //这里我就直接赋值
                    User user = new User();
                    user.setUname(row.getCell(0).getStringCellValue());
                    user.setUpassword(row.getCell(1).getStringCellValue());
                    user.setUsex(row.getCell(2).getStringCellValue());
                    user.setRole(row.getCell(3).getStringCellValue());
                    user.setUlove((int) row.getCell(4).getNumericCellValue());
                    user.setUphoto(row.getCell(5).getStringCellValue());
                    user.setUaddress(row.getCell(6).getStringCellValue());
                    users.add(user);
                }
            }
            //保存数据
            saveBatch(users);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.info("error:{}",e);
        }

        return Result.error("300","导入失败");
    }

    /**
     * 判断值的类型
     */
    public String getValue(HSSFCell cell) {

        if(cell==null){
            return "";
        }
        String cellValue= "";
        try {
            DecimalFormat df=new DecimalFormat("0.00");
            if(cell.getCellType()== CellType.NUMERIC){
                //日期时间转换
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    cellValue=DateFormatUtils.format(cell.getDateCellValue(),"yyyy-MM-dd");
                }else{
                    NumberFormat instance = NumberFormat.getInstance();
                    cellValue=instance.format(cell.getNumericCellValue()).replace(",","");
                }

            }else if(cell.getCellType() == CellType.STRING){
                //字符串
                cellValue=cell.getStringCellValue();
            }else if(cell.getCellType() == CellType.BOOLEAN){
                //Boolean
                cellValue= String.valueOf(cell.getBooleanCellValue());
            }else if(cell.getCellType() == CellType.ERROR){
                //错误
            }else if(cell.getCellType() == CellType.FORMULA){
                //转换公式 保留两位
                cellValue=df.format(cell.getNumericCellValue());
            }else{
                cellValue=null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            cellValue="-1";
        }

        return cellValue;
    }

    /**
     * 导出
     * @param response
     * @return
     */
    @Override
    public Result userExportExcel(HttpServletResponse response) {
        try {
            //创建excel
            XSSFWorkbook sheets = new XSSFWorkbook();
            //创建行
            XSSFSheet sheet = sheets.createSheet("用户信息");
            //格式设置
            XSSFCellStyle cellStyle = sheets.createCellStyle();
            //横向居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //创建单元格第一列
            XSSFRow row = sheet.createRow(0);
            //表头
            this.titleExcel(row,cellStyle);
            //查询全部的用户数据  mybatis-plus
            List<User> list = list();
            //遍历设置值
            for(int i=0;i<list.size();i++){
                XSSFRow rows = sheet.createRow(i+1);
                User user=list.get(i);
                //表格里赋值
                this.titleExcelValue(user,rows,cellStyle);
            }
            //设置浏览器响应格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String filName= URLEncoder.encode("用户信息","UTF-8");
            response.setHeader("Content-Disposition","attachment;filename="+filName+".xls");

            ServletOutputStream outputStream=response.getOutputStream();
            sheets.write(outputStream);
            outputStream.close();
            sheets.close();
            return Result.success();

        }catch (Exception e){
            e.printStackTrace();
            log.info("error:{}",e);
        }

        return Result.error("300","导出失败");
    }

    public void titleExcelValue(User user, XSSFRow row,XSSFCellStyle cellStyle) {
        XSSFCell cellId = row.createCell(0);
        cellId.setCellValue(user.getUid());
        cellId.setCellStyle(cellStyle);

        XSSFCell cellUserName = row.createCell(1);
        cellUserName.setCellValue(user.getUname());
        cellUserName.setCellStyle(cellStyle);

        XSSFCell cellPassword = row.createCell(2);
        cellPassword.setCellValue(user.getUpassword());
        cellPassword.setCellStyle(cellStyle);

        XSSFCell cellSex = row.createCell(3);
        cellSex.setCellValue(user.getUsex());
        cellSex.setCellStyle(cellStyle);

        XSSFCell cellRole = row.createCell(4);
        cellRole.setCellValue(user.getRole());
        cellRole.setCellStyle(cellStyle);

        XSSFCell cellLoveValue = row.createCell(5);
        cellLoveValue.setCellValue(user.getRole());
        cellLoveValue.setCellStyle(cellStyle);

        XSSFCell cellPhone = row.createCell(6);
        cellPhone.setCellValue(user.getUphoto());
        cellPhone.setCellStyle(cellStyle);

        XSSFCell cellAddress = row.createCell(7);
        cellAddress.setCellValue(user.getUaddress());
        cellAddress.setCellStyle(cellStyle);


    }

    public void titleExcel(XSSFRow row,XSSFCellStyle cellStyle){

        XSSFCell cellId = row.createCell(0);
        cellId.setCellValue("用户ID");
        cellId.setCellStyle(cellStyle);

        XSSFCell cellUserName = row.createCell(1);
        cellUserName.setCellValue("用户名");
        cellUserName.setCellStyle(cellStyle);

        XSSFCell cellPassword = row.createCell(2);
        cellPassword.setCellValue("密码");
        cellPassword.setCellStyle(cellStyle);

        XSSFCell cellSex = row.createCell(3);
        cellSex.setCellValue("性别");
        cellSex.setCellStyle(cellStyle);

        XSSFCell cellRole = row.createCell(4);
        cellRole.setCellValue("角色");
        cellRole.setCellStyle(cellStyle);

        XSSFCell cellLoveValue = row.createCell(5);
        cellLoveValue.setCellValue("爱心值");
        cellLoveValue.setCellStyle(cellStyle);

        XSSFCell cellPhone = row.createCell(6);
        cellPhone.setCellValue("电话号码");
        cellPhone.setCellStyle(cellStyle);

        XSSFCell cellAddress = row.createCell(7);
        cellAddress.setCellValue("地址");
        cellAddress.setCellStyle(cellStyle);

    }



    /**
     * 获取用户信息
     * @param userDto
     * @return
     */
    public User getControllerInfo(UserDto userDto){
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("uname",userDto.getUname());
        objectQueryWrapper.eq("upassword",userDto.getUpassword());
        return  getOne(objectQueryWrapper);
    }
}
