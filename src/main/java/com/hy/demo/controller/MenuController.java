package com.hy.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.entity.Menu;
import com.hy.demo.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 分页查询对应菜单信息
     */

    @GetMapping("/page")
    public Result selectMenuList(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String name
                                 ){
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.orderByAsc("sort_num");
        if(!StringUtils.isEmpty(name)){
            menuQueryWrapper.like("name",name);
        }
        Page<Menu> page = iMenuService.page(new Page<>(pageNum, pageSize), menuQueryWrapper);

        return Result.success(page);
    }

    /**
     * 查找对应的图标
     * @return
     */
    @GetMapping("/icons")
    public Result selectIcons(){

        return Result.success();
    }

    /**
     * 查询菜单栏全部信息
     * @return
     */
    @GetMapping("/menuList")
    public Result selectMenuList(@RequestParam(defaultValue = " ") String name){

        Result result=iMenuService.selectMenuList(name);
        return result;
    }

    /**
     * 查询全部菜单栏ID
     */

    @GetMapping("/ids")
    public Result selectMenuIds(){
        //查询全部的
        List<Menu> list = iMenuService.list();
        List listIds=new ArrayList<>();
        list.forEach(m->{
            listIds.add(m.getId());
        });
        return Result.success(listIds);
    }

    @PostMapping("/saveUpdate")
    public Result saveUpdateMenu(@RequestBody Menu menu){

        iMenuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("/del/{id}")
    public Result delMenuById(@PathVariable Integer id){
        iMenuService.removeById(id);
        return Result.success();
    }
}

