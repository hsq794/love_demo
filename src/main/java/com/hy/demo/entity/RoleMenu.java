package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-04-04
 */
@Getter
@Setter
  @TableName("role_menu")
@ApiModel(value = "RoleMenu对象", description = "")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("角色id")
        private Integer roleId;

      @ApiModelProperty("菜单id")
        private Integer menuId;


}
