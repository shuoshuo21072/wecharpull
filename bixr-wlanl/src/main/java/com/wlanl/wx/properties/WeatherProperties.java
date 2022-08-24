package com.wlanl.wx.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气 属性
 * @author wlanL
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherProperties {
    private String key;
    private String weatherPrefix;
    private String weatherSuffix;
}
