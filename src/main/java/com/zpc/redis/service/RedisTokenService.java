package com.zpc.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * 生成token并且放到redis中
 */
@Service
public class RedisTokenService {

    private static final Integer TOKEN_TIMEOUT = 600;

    @Autowired
    RedisService redisService;

    public String getToken() {
        String token = "token" + UUID.randomUUID();
        redisService.set(token, token, TOKEN_TIMEOUT);
        return token;
    }
    public void setName(String name){
        redisService.set(name,name);
    }

    public boolean findToken(String tokenKey){
        String token = redisService.get(tokenKey);
        if(StringUtils.isEmpty(token)){
            return false;
        }
        redisService.del(tokenKey);
        return true;
    }
    public boolean findName(String name){
       String names =  redisService.get(name);
        if(StringUtils.isEmpty(names)){
            return true;
        }else if(names.equals(name)){
           return false;
       }
       return true;
    }
}
