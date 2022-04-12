package com.hy.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.demo.mapper.ControllerMapper;
import com.hy.demo.entity.Controller;
import com.hy.demo.service.ControllerService;
import com.hy.demo.util.Result;
import com.hy.demo.vo.ControllerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controller")

public class Controllers {


    @Autowired
    private ControllerMapper controllerMapper;

    @Autowired
    private ControllerService controllerService;

    @GetMapping("/controllerList")
    public List<Controller> test(){

        return controllerMapper.findAll();
    }

    @PostMapping("/login")
    public Result login(@RequestBody ControllerDto controllerDto){

        Result result = controllerService.login(controllerDto);
        return result;
    }
    //register
    @PostMapping("/register")
    public Result register(@RequestBody ControllerDto controllerDto){

        Result result = controllerService.register(controllerDto);
        return result;
    }

    @PostMapping("/password")
    public Result updatePassword(@RequestBody ControllerDto controllerDto){
       Result result= controllerService.password(controllerDto);
        return result;
    }

    @GetMapping("/cid/{id}")
    public Result selectById(@PathVariable Integer id){
        QueryWrapper<Controller> controllerQueryWrapper = new QueryWrapper<>();
        controllerQueryWrapper.eq("cid",id);
        List<Controller> list = controllerService.list(controllerQueryWrapper);
        return Result.success(list.get(0));
    }

}
