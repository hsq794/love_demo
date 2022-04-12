package com.hy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hy.demo.entity.Menu;
import com.hy.demo.mapper.MenuMapper;
import com.hy.demo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.util.Result;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public Result selectMenuList(String name) {

        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.orderByAsc("sort_num");
        if(!StringUtils.isBlank(name)){
            menuQueryWrapper.like("name",name);
        }
        //查询全部的
        List<Menu> list = list(menuQueryWrapper);
        //先筛选一级菜单
        List<Menu> collect = list.stream().filter(m -> m.getPid() == null).collect(Collectors.toList());
        for(Menu menu:collect){
            //找出对应的父级id对应的子菜单
            menu.setChildren(list.stream().filter(m->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }

        return Result.success(collect);
    }

    @Override
    public Menu selectMenuById(Integer menuId) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq("id",menuId);
        List<Menu> list = list(menuQueryWrapper);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
