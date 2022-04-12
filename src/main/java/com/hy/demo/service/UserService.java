package com.hy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.User;
import com.hy.demo.util.Result;
import com.hy.demo.vo.UserDto;


public interface UserService extends IService<User> {


    Result login(UserDto userDto);

    Result register(UserDto userDto);

    Result updatePassword(UserDto userDto);
}
