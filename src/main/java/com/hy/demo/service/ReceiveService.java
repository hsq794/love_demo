package com.hy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Receive;
import com.hy.demo.util.Result;
import com.hy.demo.vo.ReceiveDto;

public interface ReceiveService extends IService<Receive> {


    Result donationListByPage(Integer pageNum, Integer pageSize, String uid, String gid, String rnumber);

    Result addOrUpdateUser(ReceiveDto receiveDto);
}
