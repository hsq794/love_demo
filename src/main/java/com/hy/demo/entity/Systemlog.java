package com.hy.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-20
 */
@Getter
@Setter
@ApiModel(value = "Systemlog对象", description = "")
public class Systemlog implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String userName;

      @ApiModelProperty("用户ID")
      private Integer userId;

      @ApiModelProperty("操作描述")
      private String operate;

      @ApiModelProperty("模块")
      private String module;

      @ApiModelProperty("创建日志时间")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;

      @ApiModelProperty("操作结果")
      private String result;


}
