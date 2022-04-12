package com.hy.demo.service;

import com.hy.demo.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.util.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
public interface IRoleMenuService extends IService<RoleMenu> {


     Result saveRoleMenu(Integer roleId, List<Integer> menuIds);
}
