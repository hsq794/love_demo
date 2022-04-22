package com.hy.demo.controller;



import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.common.GlobalConstant;
import com.hy.demo.config.SystemControllerLog;
import com.hy.demo.entity.User;
import com.hy.demo.service.UserService;
import com.hy.demo.util.RedisUtils;
import com.hy.demo.util.Result;
import com.hy.demo.vo.UserDto;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/userListPage")
    @SystemControllerLog(operate = "用户查询",module = "用户管理")
    public Result findUserList( @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username,
                                @RequestParam String loveValue,
                                @RequestParam String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //查询条件
        if(!StringUtils.isEmpty(username)){
            queryWrapper.like("uname",username);
        }
        if(!StringUtils.isEmpty(loveValue)){
            Integer ulove=Integer.valueOf(loveValue);
            System.out.println("ulove:"+ulove);
            if(ulove>=0){
                queryWrapper.eq("ulove",ulove);
            }
        }
        if(!StringUtils.isEmpty(address)){
            queryWrapper.like("uaddress",address);
        }
        return Result.success(userService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    @PostMapping("/addOrUpdate")
    @SystemControllerLog(operate = "用户修改或者添加",module = "用户管理")
    public Result addOrUpdateUser(@RequestBody User user){

        try {
            if(user.getUid()==null){
                //查询用户名不能重复
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uname",user.getUname());
                List<User> list = userService.list(userQueryWrapper);
                if(list.size()>0){
                    return Result.error("300","用户:["+user.getUname()+"]已存在!");
                }
                user.setUpassword("123456");
            }

            userService.saveOrUpdate(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @DeleteMapping("/del/{id}")
    @SystemControllerLog(operate = "用户删除",module = "用户管理")
    public Result delUser(@PathVariable Integer id){

        boolean b = userService.removeById(id);
        if(b){
            redisUtils.del(GlobalConstant.REDIS_KEY_TOKEN+id);
            return Result.success();
        }else{
            return Result.error("300","操作失败!");
        }

    }

    @PostMapping("/del/batch")
    @SystemControllerLog(operate = "用户大批量删除",module = "用户管理")
    public Result delBatchUser(@RequestBody List<Integer> ids){
        try{
            userService.removeBatchByIds(ids);
            List<String> objects = new ArrayList<>();
            for(Integer id:ids){
                redisUtils.del(GlobalConstant.REDIS_KEY_TOKEN+id);

            }

            return Result.success();
        }catch (Exception e){
            return Result.error("300","操作失败!");
        }

    }

    /**
     * 用户登录
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){

        Result result= userService.login(userDto);
        return result;
    }


    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){

        Result result=userService.register(userDto);
        return result;
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserDto userDto){
        Result result=userService.updatePassword(userDto);
        return result;
    }

    @GetMapping("/uid/{id}")
    @SystemControllerLog(operate = "用户ID查询",module = "用户管理")
    public Result selectById(@PathVariable Integer id){
        QueryWrapper<User> controllerQueryWrapper = new QueryWrapper<>();
        controllerQueryWrapper.eq("uid",id);
        List<User> list = userService.list(controllerQueryWrapper);
        return Result.success(list.get(0));
    }

    /**
     * Excel导入 方法一
     */
    //@PostMapping("/import")
    public Result userImport(MultipartFile file) throws Exception{
        System.out.println(file.toString());
        //InputStream inputStream = multipartFile.getInputStream();
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = new ArrayList<>();
        for(List<Object> row : list){
            User user = new User();
            user.setUname(row.get(0).toString());
            user.setUpassword(row.get(1).toString());
            user.setUsex(row.get(2).toString());
            user.setRole(row.get(3).toString());
            user.setUlove(Integer.valueOf(row.get(4).toString()));
            user.setUphoto(row.get(5).toString());
            user.setUaddress(row.get(6).toString());
            users.add(user);
        }
        //批量插入用户信息
        userService.saveBatch(users);
        return Result.success();
    }

    /**
     * Excel导入  方法2
     */
    @PostMapping("/import")
    public Result userImport2(@RequestParam("file") MultipartFile file) throws Exception{
        Result result=userService.userImportExcel(file);
        return result;
    }



    /**
     * Excel导出 方法一
     */
    //@GetMapping("/export")
    public Result userExport(HttpServletResponse response) throws Exception{
        //查询全部的用户数据
        List<User> list = userService.list();
        //在内存里做操作，保存到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("uname","用户名");
        writer.addHeaderAlias("upassword","密码");
        writer.addHeaderAlias("usex","性别");
        writer.addHeaderAlias("role","角色");
        writer.addHeaderAlias("ulove","爱心值");
        writer.addHeaderAlias("uphoto","电话号码");
        writer.addHeaderAlias("uaddress","地址");
        //一次性写出list内的对象的Excel，使用默认样式，强制输出标题
        writer.write(list,true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filName= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+filName+".xls");

        ServletOutputStream outputStream=response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
        return Result.success();
    }

    /**
     * 导出 方法二
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/export")
    public Result userExport2(HttpServletResponse response) throws Exception{
        Result result=userService.userExportExcel(response);
        return result;
    }

}
