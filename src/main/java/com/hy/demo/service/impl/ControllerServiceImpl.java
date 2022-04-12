package com.hy.demo.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.mapper.ControllerMapper;
import com.hy.demo.entity.Controller;
import com.hy.demo.service.ControllerService;
import com.hy.demo.util.Result;
import com.hy.demo.util.TokenUtils;
import com.hy.demo.vo.ControllerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ControllerServiceImpl extends ServiceImpl<ControllerMapper, Controller> implements ControllerService {

    @Autowired
    private ControllerMapper controllerMapper;

    @Override
    public Result login(ControllerDto controllerDto) {
        //判断空
        String cname = controllerDto.getCname();
        String cpassword = controllerDto.getCpassword();
        if(StringUtils.isBlank(cname) || StringUtils.isBlank(cpassword)){
            return Result.error("300","参数错误");
        }
       //获取用户信息
        Controller one = getControllerInfo(controllerDto);
        if(!Objects.equals(one,null)){
            BeanUtil.copyProperties(one,controllerDto,true);
            String token = TokenUtils.genToken(one.getCid().toString(), one.getCpassword());
            controllerDto.setToken(token);
            return Result.success(controllerDto);
        }
        return Result.error("300","用户名或者密码错误");
    }

    @Override
    public Result register(ControllerDto controllerDto) {
        String cname = controllerDto.getCname();
        String cpassword = controllerDto.getCpassword().trim();
        String confirmPassword=controllerDto.getConfirmPassword().trim();
        if(StringUtils.isBlank(cname) || StringUtils.isBlank(cpassword) || StringUtils.isBlank(confirmPassword)){
            return Result.error("300","参数错误");
        }
        if(!cpassword.equals(confirmPassword)){
            return Result.error("300","输入的两次密码不一致");
        }
        Controller controllerInfo = getControllerInfo(controllerDto);
        if(controllerInfo==null){
            controllerInfo=new Controller();
            //BeanUtil.copyProperties(one,controllerDto,true);
            controllerInfo.setCname(cname);
            controllerInfo.setCpassword(cpassword);
            save(controllerInfo);
            return Result.success(controllerInfo);
        }else{
            return Result.error("300","用户信息已存在");
        }
        //return null;
    }

    @Override
    public Result password(ControllerDto controllerDto) {
        String cpassword = controllerDto.getCpassword().trim();
        String confirmPassword=controllerDto.getConfirmPassword().trim();
        String newPassword = controllerDto.getNewPassword();
        Integer cid = controllerDto.getCid();
        if(cid==null || StringUtils.isBlank(cpassword) || StringUtils.isBlank(confirmPassword) || StringUtils.isBlank(newPassword)){
            return Result.error("300","参数错误");
        }
        if(!newPassword.equals(confirmPassword)){
            return Result.error("300","输入的两次密码不一致");
        }
        int i = controllerMapper.updatePassword(newPassword, cid, cpassword);
        if (i>0){
            return Result.success();
        }
        return Result.error("300","操作失败");
    }

    public Controller getControllerInfo(ControllerDto controllerDto){
        QueryWrapper<Controller> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("cname",controllerDto.getCname());
        objectQueryWrapper.eq("cpassword",controllerDto.getCpassword());
        return  getOne(objectQueryWrapper);
    }
}
