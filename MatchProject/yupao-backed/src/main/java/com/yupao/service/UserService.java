package com.yupao.service;

import com.yupao.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Beats
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-22 16:20:27
*/
public interface UserService extends IService<User> {
    public List<User> getAllUser();
}
