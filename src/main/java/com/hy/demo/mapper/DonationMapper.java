package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.entity.Donation;
import com.hy.demo.vo.DonationDto;


public interface DonationMapper extends BaseMapper<Donation> {


    Page<DonationDto> findPage(Page<Object> objectPage, String uname, String gid, String dnumber);
}
