package com.xj;

import com.xj.xjapiclientsdk.client.XjApiClient;
import com.xj.xjapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class XjapiInterfaceApplicationTests {
    
    @Resource
    private XjApiClient xjApiClient;
    
    @Test
    void contextLoads() {
        String res = xjApiClient.getNameByGet("scintilla");
        User user = new User();
        user.setUsername("scintilla");
        String userNameByPost = xjApiClient.getUserNameByPost(user);
        System.out.println(res);
        System.out.println(userNameByPost);
    }
    
}
