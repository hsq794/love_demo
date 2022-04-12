package com.hy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Goods;
import com.hy.demo.util.Result;

public interface GoodsService extends IService<Goods> {

    Result donationListByPage(Integer pageNum, Integer pageSize, String gname, String glove, String gnumber);
}
