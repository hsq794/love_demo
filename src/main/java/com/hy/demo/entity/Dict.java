package com.hy.demo.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-05
 */
@Getter
@Setter
  @ApiModel(value = "Dict对象", description = "")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("名称")
      private String name;

      @ApiModelProperty("内容")
      private String value;

      @ApiModelProperty("类型")
      private String type;


}
