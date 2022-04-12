package com.hy.demo.mapper;

import com.hy.demo.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    int deleteByRoleId(Integer roleId);

}
