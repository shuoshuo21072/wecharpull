package com.wlanl.wx.task;

import com.wlanl.wx.service.WeCharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wlanL
 */
@Component
public class SendTask {

    @Value("${third.part.weather.city}")
    private String city;

    @Autowired
    private WeCharService weCharService;

    @Scheduled(cron = "${cron}")
    public void send(){
        weCharService.pushMessage(city);
    }

}
