package com.xj.xjapiinterface.controller;

import com.xj.xjapiclientsdk.model.User;
import com.xj.xjapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName NameController
 * @Description TODO
 * @Author 嘻精
 * @Date 2023/5/20 13:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name) {
       
        return "GET  你的名字是" + name;
    }
    
    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST  你的名字是" + name;
    }
    
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey") ;
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // TODO 实际情况是去数据库中查询是否已经分配给用户
        if (!accessKey.equals("xj")) {
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        if ((System.currentTimeMillis() / 1000) - Long.parseLong(timestamp) > 5 * 60) {
            throw new RuntimeException("无权限");
        }
        // TODO 实际情况是去数据库中查询secretKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }
        return "POST  用户名是" + user.getUsername();
    }
}
