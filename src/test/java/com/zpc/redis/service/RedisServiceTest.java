package com.zpc.redis.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisServiceTest {
    @Autowired
    RedisService1 redisService;

    @Test
    public void set() throws Exception {
        redisService.set("keyx", "valuex");
    }

    @Test
    public void get() throws Exception {
        System.out.println("\n=============res:" + redisService.get("keyx")+"\n");
    }
}
