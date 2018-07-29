package com.github.felton.disconf.generator.example;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by felton on 2018/7/29.
 */
@SpringBootApplication
public class Main{

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @Bean(destroyMethod = "destroy")
    DisconfMgrBean disconfMgrBean(){
        DisconfMgrBean bean = new DisconfMgrBean();
        bean.setScanPackage("com.github.felton.disconf.generator.example");
        return bean;
    }

    @Bean(destroyMethod = "destroy")
    DisconfMgrBeanSecond disconfMgrBeanSecond(){
        DisconfMgrBeanSecond beanSecond = new DisconfMgrBeanSecond();
        return beanSecond;
    }
}
