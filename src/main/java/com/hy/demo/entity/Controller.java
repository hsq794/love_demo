package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@TableName(value = "controller")
@NoArgsConstructor
@AllArgsConstructor

public class Controller {

    @TableId(type = IdType.AUTO) //id 自动
    private Integer cid;
    private String cname;
    private String cpassword;

}
