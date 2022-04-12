package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receive {

    @TableId(value = "rid",type = IdType.AUTO)
    private Integer rid;

    private Integer runmber;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date rtime;

    private Integer uid;

    private Integer gid;



}
