package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.entity.Receive;
import com.hy.demo.vo.ReceiveDto;

public interface ReceiveMapper extends BaseMapper<Receive> {

    Page<ReceiveDto> findReceivePage(Page<Object> objectPage, String uname, String gid, String rnumber);

}
