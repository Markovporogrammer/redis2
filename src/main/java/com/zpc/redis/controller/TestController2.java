package com.zpc.redis.controller;

import com.zpc.redis.annotation.ExtApiIdempotent;
import com.zpc.redis.annotation.ExtApiToken;
import com.zpc.redis.service.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController2 {

    @Autowired
    RedisTokenService tokenService;

    @RequestMapping(value = "/indexPage")
    @ExtApiToken
    public String indexPage(HttpServletRequest request) {
        System.out.println("================================");
        //加上注解ExtApiToken，使用AOP方式统一设置token
        //request.setAttribute("token",tokenService.getToken());
        return "indexPage";
    }

    @RequestMapping(value = "/addUserPage")
    @ResponseBody
    @ExtApiIdempotent(type = "form")
    public String addUserPage(HttpServletRequest request) {
        return "添加成功！";
    }
}
