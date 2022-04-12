package com.hy.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.entity.Role;
import com.hy.demo.entity.RoleMenu;
import com.hy.demo.service.IRoleMenuService;
import com.hy.demo.service.IRoleService;
import com.hy.demo.util.Result;
import io.swagger.models.auth.In;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;


    @GetMapping("/page")
    public Result roleList(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            roleQueryWrapper.like("name",name);
        }
        Page<Role> page = iRoleService.page(new Page<>(pageNum, pageSize), roleQueryWrapper);
        return Result.success(page);

    }

    @PostMapping("/saveUpdate")
    public Result saveUpdate(@RequestBody Role role){

        iRoleService.saveOrUpdate(role);

        return Result.success();
    }

    @DeleteMapping("/del/{id}")
    public Result delRole(@PathVariable Integer id){
        iRoleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/findAll")
    public Result selectRoles(){
        return Result.success(iRoleService.list());
    }

}

