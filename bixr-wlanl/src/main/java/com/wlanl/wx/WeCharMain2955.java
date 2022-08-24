package com.wlanl.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类  开启定时任务
 * @author wlanL
 */
@EnableScheduling
@SpringBootApplication
public class WeCharMain2955 {

    public static void main(String[] args) {
        SpringApplication.run(WeCharMain2955.class,args);
    }

}
