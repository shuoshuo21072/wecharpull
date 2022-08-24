package com.wlanl.wx.app;

import com.wlanl.wx.task.SendTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeCharController {

    @Autowired
    private SendTask sendTask;


    @GetMapping("/send")
    public void pushWeCharTaskMessage(){
        // 推送定时任务
        sendTask.send();
    }

}
