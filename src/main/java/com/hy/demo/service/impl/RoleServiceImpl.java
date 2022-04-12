package com.hy.demo.service.impl;

import com.hy.demo.entity.Role;
import com.hy.demo.mapper.RoleMapper;
import com.hy.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 胡萝卜
 * @since 2022-04-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
