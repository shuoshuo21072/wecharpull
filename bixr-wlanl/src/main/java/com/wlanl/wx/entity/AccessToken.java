package com.wlanl.wx.entity;

import lombok.Data;

/**
 * @author wlanL
 */
@Data
public class AccessToken {

    private String accessToken;
    private long expiresIn;

    public AccessToken(String accessToken, long expiresIn){
        this.accessToken = accessToken;
        this.expiresIn = System.currentTimeMillis() + (expiresIn*1000);
    }

    /**
     * 是否过期
     * @return tf
     */
    public boolean isExpires(){
        return System.currentTimeMillis() > this.expiresIn;
    }


}
