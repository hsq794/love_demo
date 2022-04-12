package com.hy.demo.service;

import com.hy.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
public interface IMenuService extends IService<Menu> {

    Result selectMenuList(String name);

    Menu selectMenuById(Integer menuId);
}
