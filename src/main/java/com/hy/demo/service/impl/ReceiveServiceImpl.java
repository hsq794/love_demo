package com.hy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.mapper.ReceiveMapper;
import com.hy.demo.entity.Receive;
import com.hy.demo.entity.User;
import com.hy.demo.service.ReceiveService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.Result;
import com.hy.demo.vo.ReceiveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveServiceImpl extends ServiceImpl<ReceiveMapper, Receive> implements ReceiveService {

    @Autowired
    private ReceiveMapper receiveMapper;

    @Autowired
    private UserService userService;

    @Override
    public Result donationListByPage(Integer pageNum, Integer pageSize, String uname, String gid, String rnumber) {
        try{
            QueryWrapper<Receive> queryWrapper = new QueryWrapper<>();
            Page<ReceiveDto> page=receiveMapper.findReceivePage(new Page<>(pageNum, pageSize),uname,gid,rnumber);
            return Result.success(page);

        }catch (Exception e){
            e.printStackTrace();
            return Result.error("300",e.toString());
        }

    }

    @Override
    public Result addOrUpdateUser(ReceiveDto receiveDto) {

        //判空条件

        Receive receive = new Receive();
        if(receiveDto.getRid()!=null){
            receive.setRid(receiveDto.getRid());
        }

        String uname = receiveDto.getUname();
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty("uname")){
            objectQueryWrapper.eq("uname",uname);
        }
        List<User> list = userService.list(objectQueryWrapper);

        if(list.size()>0){
            receive.setUid(list.get(0).getUid());
        }

        receive.setGid(receiveDto.getGid());
        receive.setRtime(receiveDto.getRtime());
        receive.setRunmber(receiveDto.getRunmber());
        //保存
        saveOrUpdate(receive);

        return Result.success();
    }
}
