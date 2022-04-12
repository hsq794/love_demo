package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.Controller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper

public interface ControllerMapper extends BaseMapper<Controller> {

    @Select("select * from controller")
    List<Controller> findAll();

    int updatePassword(String newPassword, Integer cid, String cpassword);
}
