package com.yupao.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yupao.common.BaseResponse;
import com.yupao.common.ResultUtils;
import com.yupao.model.domain.User;
import com.yupao.service.UserService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/info")
    public BaseResponse<List<User>> getUserInfo(){
        List<User> data = userService.getAllUser();
        return ResultUtils.success(data);
    }
}
