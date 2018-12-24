package com.zpc.redis.controller;

import com.zpc.redis.annotation.ExtApiIdempotent;
import com.zpc.redis.bean.User;
import com.zpc.redis.service.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private RedisTokenService tokenService;

    @RequestMapping(value = "addUser", produces = "application/json;charset=utf-8")
    public String addUser(@RequestBody User user, HttpServletRequest request) {
        String token = request.getHeader("token");
        String name = request.getParameter("name");
        if (StringUtils.isEmpty(token)) {
            return "请求参数错误！";
        }
        boolean tokenOk = tokenService.findToken(token);
        if (!tokenOk) {
            return "请勿重复提交!";
        }


    //执行正常的业务逻辑
        System.out.println("user info:" + user);
        return "添加成功!";
    }

    @RequestMapping(value = "getToken")
    public String getToken() {
        return tokenService.getToken();
    }

    @ExtApiIdempotent(type = "head")
    @RequestMapping(value = "addUser2", produces = "application/json;charset=utf-8")
    public String addUser2(@RequestBody User user) {
        //执行正常的业务逻辑
        System.out.println("user info:" + user);
        return "添加成功!!";
    }
}
