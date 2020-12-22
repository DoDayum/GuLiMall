package com.atguigu.gulimall.product;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void redissonTest(){
        System.out.println(redissonClient);
    }

}
