package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @TableId(value ="gid",type=IdType.AUTO)
    private Integer gid;

    private String gname;

    private Integer glove;

    private Integer gnumber;
}
