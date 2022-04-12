package com.hy.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.mapper.DonationMapper;
import com.hy.demo.entity.Donation;
import com.hy.demo.entity.Goods;
import com.hy.demo.entity.User;
import com.hy.demo.service.DonationService;
import com.hy.demo.service.GoodsService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.Result;
import com.hy.demo.vo.DonationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DonationServiceImpl extends ServiceImpl<DonationMapper, Donation> implements DonationService {

    @Autowired
    private DonationMapper donationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public Result donationListByPage(Integer pageNum, Integer pageSize, String uname, String gid, String dnumber) {
        try{
            QueryWrapper<Donation> queryWrapper = new QueryWrapper<>();
            Page<DonationDto> donationDto = donationMapper.findPage(new Page<>(pageNum, pageSize), uname, gid, dnumber);
            System.out.println("donationDto:"+donationDto.toString());
            return Result.success(donationDto);

        }catch (Exception e){
            e.printStackTrace();
            return Result.error("300",e.toString());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addOrUpdateUser(DonationDto donationDto) {

        try {
            //判断空
            boolean flag=false;
            Donation oldDonation = null;
            User oldUser = null;
            Donation donation = new Donation();
            if(donationDto.getDid()!=null){
                donation.setDid(donationDto.getDid());
                //修改之前数量值  然后要加多少爱心值  数量也是一样
                List listIds = new ArrayList<>();
                listIds.add(donation.getDid());
                List<Donation> list = listByIds(listIds);
                if(list.size()>0) {
                    oldDonation=list.get(0);
                }

            }
            /*if(donationDto.getUid()!=null){
                donation.setUid(donationDto.getUid());
                List listIds = new ArrayList<>();
                listIds.add(donation.getDid());
                List<User> list =userService.listByIds(listIds);
                if(list.size()>0) {
                    oldUser=list.get(0);
                }
            }else{*/
            String uname = donationDto.getUname();
            QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty("uname")){
                objectQueryWrapper.eq("uname",uname);
            }
            List<User> userlist = userService.list(objectQueryWrapper);

            if(userlist.size()>0){
                oldUser=userlist.get(0);
                donation.setUid(userlist.get(0).getUid());
            }
//            }
            donation.setGid(donationDto.getGid());
            donation.setDtime(donationDto.getDtime());
            donation.setDunmber(donationDto.getDunmber());
            System.out.println("donation:"+donation);
            saveOrUpdate(donation);
            //添加成功，对应物品爱心值进行加上
            List listIds = new ArrayList<>();
            listIds.add(donation.getGid());
            List<Goods> list = goodsService.listByIds(listIds);
            Integer jloveValue=0;
            Integer addLoveValue=0;
            Integer loveValue=0;
            Integer jnumberAll=0;
            Integer addNumberAll=0;
            Integer numberAll=0;

            for (Goods g : list) {
                if(!Objects.equals(oldDonation,null)){
                    //
                    Integer dunmber = oldDonation.getDunmber();
                    if(dunmber>donation.getDunmber()){
                        //减少了
                        System.out.println("减少。。。。");
                        jnumberAll=dunmber-donation.getDunmber();

                    }else{
                        if(dunmber.equals(donation.getDunmber())){
                            return  Result.success();
                        }
                        //增加
                        System.out.println("增加。。。。");
                        addNumberAll=donation.getDunmber()-dunmber;

                    }
                }else{
                    System.out.println("增加2。。。。");
                    addNumberAll=donation.getDunmber();
                }
                if(jnumberAll>0){
                    jloveValue = g.getGlove() * (jnumberAll);
                    numberAll=g.getGnumber()-jnumberAll;
                    loveValue=oldUser.getUlove()-jloveValue;
                }
                if(addNumberAll>0){
                    addLoveValue = g.getGlove() * (addNumberAll);
                    numberAll=g.getGnumber()+addNumberAll;
                    loveValue=addLoveValue+oldUser.getUlove();
                }

            }
            if(!flag){
                //物品数量加上
                // numberAll+=donation.getDunmber();
                list.get(0).setGnumber(numberAll);
                goodsService.saveOrUpdate(list.get(0));
                //更新用户爱心值
                UpdateWrapper<User> userQueryWrapper = new UpdateWrapper<>();
                userQueryWrapper.set("ulove",loveValue);
                userQueryWrapper.eq("uid",donation.getUid());
                userService.update(userQueryWrapper);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("300",e.toString());
        }

        return Result.success();
    }
}
