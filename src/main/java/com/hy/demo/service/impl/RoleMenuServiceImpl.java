package com.hy.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hy.demo.entity.Menu;
import com.hy.demo.entity.RoleMenu;
import com.hy.demo.exception.ServiceException;
import com.hy.demo.mapper.RoleMenuMapper;
import com.hy.demo.service.IMenuService;
import com.hy.demo.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private IMenuService iMenuService;

    @Transactional
    @Override
    public Result saveRoleMenu(Integer roleId, List<Integer> menuIds) {
        try {
            //解除之前角色对应的菜单
            int i = roleMenuMapper.deleteByRoleId(roleId);
            //copy
            ArrayList<Integer> copyMenuIds = CollUtil.newArrayList(menuIds);
            for(Integer menuId:menuIds){
                Menu menu=iMenuService.selectMenuById(menuId);
                RoleMenu roleMenu = new RoleMenu();
                //补上没有父级ID
                if(menu.getPid()!=null && !copyMenuIds.contains(menu.getPid())){
                    roleMenu.setMenuId(menu.getPid());
                    roleMenu.setRoleId(roleId);
                    copyMenuIds.add(menu.getPid());
                }else{
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(roleId);
                }
                roleMenuMapper.insert(roleMenu);
            }

            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("300","操作失败");
        }


    }
}
