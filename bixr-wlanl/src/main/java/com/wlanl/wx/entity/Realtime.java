package com.wlanl.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 聚合天气 当天数据 实体
 * @author wlanL
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Realtime {
    private String temperature;
    private String humidity;
    private String info;
    private String direct;
    private String power;
    private String aqi;
}
