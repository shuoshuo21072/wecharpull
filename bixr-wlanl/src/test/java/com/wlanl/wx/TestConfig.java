package com.wlanl.wx;

import com.wlanl.wx.properties.GirlFriendProperties;
import com.wlanl.wx.properties.WeCharMessageModelProperties;
import com.wlanl.wx.properties.WeatherProperties;
import com.wlanl.wx.utils.JsonUtils;
import com.wlanl.wx.utils.ThirdPartUtil;
import com.wlanl.wx.utils.TokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConfig {

    @Value("classpath:messageModel.json")
    private Resource resource;

    @Autowired
    private WeCharMessageModelProperties weCharMessageModelProperties;
    public static final String TOKEN =
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    @Autowired
    private WeatherProperties weatherProperties;

    @Autowired
    private GirlFriendProperties girlFriendProperties;

    @Test
    public void test() throws IOException {
        // String json = JsonUtils.getJson(resource);
        // System.out.println(json);
        // System.out.println(weatherProperties.getWeatherPrefix()+"邯郸"+weatherProperties.getWeatherSuffix());
        // System.out.println(weCharMessageModelProperties.getToken());
        // String accessToken = TokenUtil.getAccessToken();
        // System.out.println(accessToken);
        // String url = TOKEN + accessToken;
        // System.out.println(url);
        System.out.println(girlFriendProperties.getBirthday() + "\t\t\t\t" + girlFriendProperties.getMeetTheTime());
        System.out.println(weCharMessageModelProperties.getToUser());

    }
}
