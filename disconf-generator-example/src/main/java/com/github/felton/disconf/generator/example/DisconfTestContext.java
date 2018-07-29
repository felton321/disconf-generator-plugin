package com.github.felton.disconf.generator.example;

import com.github.felton.disconf.generator.example.config.Redis;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by felton on 2018/7/29.
 */
@Component
public class DisconfTestContext implements ApplicationContextAware{

    @Autowired
    Redis redis;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("redis.host: " + redis.getRedisHost());
        System.out.println("redis.port: " + redis.getRedisPort());
    }
}
