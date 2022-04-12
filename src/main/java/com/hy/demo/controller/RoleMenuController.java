package com.hy.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.demo.entity.RoleMenu;
import com.hy.demo.service.IRoleMenuService;
import com.hy.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    private IRoleMenuService iRoleMenuService;

    /**
     * 根据角色ID查询对应下面的已有的菜单进行展示
     */
    @GetMapping("/{roleId}")
    public Result selectByRoleId(@PathVariable Integer roleId){

        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();

        roleMenuQueryWrapper.eq("role_id",roleId);

        List<RoleMenu> list = iRoleMenuService.list(roleMenuQueryWrapper);
        //所有的菜单ID
        List listIds=new ArrayList<>();

        list.forEach(roleMenu -> {
            listIds.add(roleMenu.getMenuId());
        });

        return Result.success(listIds);
    }

    /**
     * 根据角色ID进行绑定菜单栏展示
     */
    @PostMapping("/{roleId}")
    public Result saveRoleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        Result result= iRoleMenuService.saveRoleMenu(roleId,menuIds);
        return result;
    }


}

