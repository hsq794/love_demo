package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.User;

public interface UserMapper extends BaseMapper<User> {


    int updatePassword(String newPassword, Integer cid, String upassword);
}
