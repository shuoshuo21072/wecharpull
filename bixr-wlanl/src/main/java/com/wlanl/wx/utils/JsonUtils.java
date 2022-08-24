package com.wlanl.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wlanl.wx.entity.MsgDataModel;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author wlanL
 */
public class JsonUtils {

    /**
     * 读JSON文件
     */
    public static String getJson(Resource resource) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuilder message=new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null) {
            message.append(line);
        }
        String defaultString = message.toString();
        return defaultString.replace("\r\n", "").replaceAll(" +", "");
    }

    /**
     * 摘取 json中的 data 数据
     * @param json  总数居
     * @return data
     */
    public static Map<String,Object> getJsonToMap(String json){
        Map<String, Object> info = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        Map<String, MsgDataModel> data = JSON.parseObject(info.get("data").toString(), new TypeReference<Map<String, MsgDataModel>>() {});
        info.put("data",data);
        return info;
    }

}
