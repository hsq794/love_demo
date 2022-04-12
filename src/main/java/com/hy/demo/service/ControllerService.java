package com.hy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Controller;
import com.hy.demo.util.Result;
import com.hy.demo.vo.ControllerDto;

public interface ControllerService extends IService<Controller> {


    Result login(ControllerDto controllerDto);

    Result register(ControllerDto controllerDto);

    Result password(ControllerDto controllerDto);
}
