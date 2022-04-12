package com.hy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.mapper.GoodsMapper;
import com.hy.demo.entity.Goods;
import com.hy.demo.service.GoodsService;
import com.hy.demo.util.Result;
import org.springframework.stereotype.Service;

@Service
public class GoodsServcieImpl extends ServiceImpl<GoodsMapper,Goods >  implements GoodsService {

    @Override
    public Result donationListByPage(Integer pageNum, Integer pageSize, String gname, String glove, String gnumber) {
        try{
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            //查询条件
            if(!StringUtils.isEmpty(gname)){
                System.out.println(gname);
                Integer gid=Integer.valueOf(gname);
                //queryWrapper.like("gname",gname);
                queryWrapper.eq("gid",gid);
            }
            if(!StringUtils.isEmpty(glove)){
                queryWrapper.eq("glove",glove);
            }
            if(!StringUtils.isEmpty(gnumber)){
                queryWrapper.eq("gnumber",gnumber);
            }
            Page<Goods> page = page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);

        }catch (Exception e){
            e.printStackTrace();
            return Result.error("300",e.toString());
        }

    }
}
