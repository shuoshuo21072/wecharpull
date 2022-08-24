package com.wlanl.wx.service;

/**
 * WeChar 业务接口
 * @author wlanL
 */
public interface WeCharService {


    /**
     * 推送消息
     * @param city 城市地址
     */
    void pushMessage(String city);
}
