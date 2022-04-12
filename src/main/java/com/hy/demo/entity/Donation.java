package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {

    @TableId(value = "did",type = IdType.AUTO)
    private Integer did;
    private Integer dunmber;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date dtime;
    private Integer uid;
    private Integer gid;
}
