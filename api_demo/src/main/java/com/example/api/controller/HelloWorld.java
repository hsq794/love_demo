package com.example.api.controller;

import com.example.api.common.Result;
import com.example.api.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hsq
 */
@RestController
@RequestMapping("/api")
public class HelloWorld {

    private static final Logger log= LoggerFactory.getLogger(HelloWorld.class);


    @GetMapping ("/getHello")
    public Result getHelloWord(){
        log.info("进入到api接口.......");
        return Result.success("hello world api get 接口数据");
    }

    @PostMapping("/postHello")
    public Result postHelloWord(@RequestBody User user){
        log.info("进入post 方法.....");
        System.out.println(user.toString());
        return Result.success("hello world api post接口数据");
    }

}

