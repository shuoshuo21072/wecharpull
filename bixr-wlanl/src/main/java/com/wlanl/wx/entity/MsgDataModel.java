package com.wlanl.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 消息数据模板
 * @author wlanL
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MsgDataModel {
    private String value;
    private String color;
    public MsgDataModel(String value){
        this.value = value;
    }
}
