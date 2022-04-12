package com.hy.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.entity.*;
import com.hy.demo.mapper.RoleMapper;
import com.hy.demo.mapper.RoleMenuMapper;
import com.hy.demo.mapper.UserMapper;
import com.hy.demo.service.IMenuService;
import com.hy.demo.service.IRoleMenuService;
import com.hy.demo.service.IRoleService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.Result;
import com.hy.demo.util.TokenUtils;
import com.hy.demo.vo.ControllerDto;
import com.hy.demo.vo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

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
            userDto.setToken(token);
            //设置动态菜单 todo
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
            return Result.success();
        }
        return Result.error("300","操作失败");

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
