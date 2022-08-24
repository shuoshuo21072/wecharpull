package com.wlanl.wx.config;

import com.wlanl.wx.properties.GirlFriendProperties;
import com.wlanl.wx.properties.WeCharMessageModelProperties;
import com.wlanl.wx.properties.WeatherProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author wlanL
 */
@Component
@SpringBootConfiguration
public class CommonThirdPartConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "wechar.argument")
    public WeCharMessageModelProperties weCharMessageModelProperties(){
        return new WeCharMessageModelProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "third.part.weatch")
    public WeatherProperties weatherProperties(){
        return new WeatherProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "girl-friend")
    public GirlFriendProperties girlFriendProperties(){
        return new GirlFriendProperties();
    }


}
