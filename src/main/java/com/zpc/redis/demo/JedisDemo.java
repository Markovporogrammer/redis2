package com.zpc.redis.demo;

import redis.clients.jedis.Jedis;

/**
 * @author Evan
 */
public class JedisDemo {
    public static void main(String[] args) {
        // 构造jedis对象
        Jedis jedis = new Jedis("120.79.236.74", 6379);
        // 向redis中添加数据
        jedis.set("name", "鸟鹏");
        // 从redis中读取数据
        String value = jedis.get("name");
        System.out.println(value);
        // 关闭连接
        jedis.close();
    }
}
