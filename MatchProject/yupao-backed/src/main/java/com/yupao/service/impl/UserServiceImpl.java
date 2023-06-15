package com.yupao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupao.model.domain.User;
import com.yupao.service.UserService;
import com.yupao.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Beats
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-22 16:20:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    public List<User> getAllUser(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("*");
        return userMapper.selectList(queryWrapper);
    }
}




