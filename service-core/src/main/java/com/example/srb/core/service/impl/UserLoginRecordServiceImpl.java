package com.example.srb.core.service.impl;

import com.example.srb.core.pojo.entity.UserLoginRecord;
import com.example.srb.core.mapper.UserLoginRecordMapper;
import com.example.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author fangzheng
 * @since 2022-04-20
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
