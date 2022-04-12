package com.hy.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.demo.entity.Goods;
import com.hy.demo.entity.User;
import com.hy.demo.service.GoodsService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;


    @GetMapping("/goodsListPage")
    public Result findUserList(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String gname,
                               @RequestParam String glove,
                               @RequestParam String gnumber) {

        Result result=goodsService.donationListByPage(pageNum,pageSize,gname,glove,gnumber);

        return result;
    }

    @PostMapping("/addOrUpdate")
    public Result addOrUpdateUser(@RequestBody Goods goods) throws Exception {
        try {
            if(goods.getGid()==null){
                QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
                goodsQueryWrapper.eq("gname", goods.getGname());
                if(goodsService.list(goodsQueryWrapper).size()>0){
                    return Result.error("300","物品:["+goods.getGname()+"]已存在!");
                }
            }
            goodsService.saveOrUpdate(goods);

            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("300","操作失败");
        }


    }

    @DeleteMapping("/del/{id}")
    public Result delUser(@PathVariable Integer id) throws Exception{
        boolean b = goodsService.removeById(id);
        if(b){
            return Result.success();
        }else{
            return Result.error("300","操作失败!");
        }

    }

    /**
     * 查找对应的物品信息
     */
    @GetMapping("/findAllGoods")
    public Result findAllGoods(){
        List<Goods> list = goodsService.list();
        return Result.success(list);
    }
    /**
     * 查找对应的物品名称
     */
    @GetMapping("/findByName")
    public Result findByName(){
        List<Goods> list = goodsService.list();
        System.out.println(list.toString());
        List<Map> listMap = new ArrayList<>();
        list.forEach(g->{
            Map map= new HashMap<>();
            map.put("value",g.getGid());
            map.put("label",g.getGname());
            listMap.add(map);
         });
        System.out.println("map:"+listMap.toString());
        return Result.success(listMap);
    }

    /**
     * 全部的总量
     */
    @GetMapping("/findChartNum")
    public Result findAllChartNum(){
        List<Goods> list = goodsService.list();
        //物品总重量
        int sumGnumber=0;
        //总爱心值
        int sumLove=0;
        for(Goods goods:list){
            sumGnumber+= goods.getGnumber();
            sumLove+=(goods.getGnumber()*goods.getGlove().longValue());
        }
        List<User> userList = userService.list();
        int sumUser=userList.size();
        List<Integer> data = new ArrayList<>();
        data.add(sumGnumber);
        data.add(sumLove);
        data.add(sumUser);
        return Result.success(data);
    }





}
