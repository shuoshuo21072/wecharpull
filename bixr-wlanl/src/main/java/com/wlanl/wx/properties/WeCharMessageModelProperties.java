package com.wlanl.wx.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信 属性信息
 * @author wlanL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeCharMessageModelProperties {
    private String toUser;
    private String appId;
    private String appSecret;
    private String token;
}
