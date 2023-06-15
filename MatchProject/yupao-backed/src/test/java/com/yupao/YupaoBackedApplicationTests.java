package com.yupao;

import com.yupao.model.domain.User;
import com.yupao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class YupaoBackedApplicationTests {

    @Resource
    private UserService userService;

    @Test
    public void testConnect(){
        List<User> userList = userService.getAllUser();
        userList.forEach(System.out::println);
    }

}
