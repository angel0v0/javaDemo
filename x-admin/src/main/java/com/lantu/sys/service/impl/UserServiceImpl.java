package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yumo
 * @since 2023-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名查询用户并结果
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空 && 密码与传入密码匹配 则生成token，并传入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            // 状态不可用
            if(loginUser.getStatus()==0){
                Map<String,Object> data = new HashMap<>();
                data.put("error","状态不可用");
                return data;
            }

            //暂时用UUID，公司用jwt
            String key = "user:"+UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,60, TimeUnit.MINUTES);

            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }

        return null;
        /*//根据用户名密码查询用户并结果
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空则生成token
        if (loginUser != null) {
            //暂时用UUID，公司用jwt
            String key = "user:"+UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,60, TimeUnit.MINUTES);

            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }

        return null;*/
    }

    @Override
    public Map<String, Object> getUserinfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj != null) {
            User loginUser =  JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String,Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }


}
