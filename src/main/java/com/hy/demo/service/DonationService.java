package com.hy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Donation;
import com.hy.demo.util.Result;
import com.hy.demo.vo.DonationDto;

public interface DonationService extends IService<Donation> {

    Result donationListByPage(Integer pageNum, Integer pageSize, String uid, String gid, String dnumber);

    Result addOrUpdateUser(DonationDto donationDto);
}
