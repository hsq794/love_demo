package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    private String uname;

    private String upassword;

    private String usex;

    private Integer ulove;

    private String uphoto;

    private String uaddress;

    private String role;



}
