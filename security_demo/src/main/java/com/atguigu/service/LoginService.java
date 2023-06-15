package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.result.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
