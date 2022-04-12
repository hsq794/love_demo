package com.hy.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.demo.entity.Dict;
import com.hy.demo.service.IDictService;
import com.hy.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private IDictService iDictService;

    @GetMapping("/icons")
    public Result selectDictIcons(){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type","icon");
        return Result.success(iDictService.list(dictQueryWrapper));
    }


}

