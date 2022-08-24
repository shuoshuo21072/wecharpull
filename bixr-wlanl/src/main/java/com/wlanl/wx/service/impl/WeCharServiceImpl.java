package com.wlanl.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.wlanl.wx.entity.MsgDataModel;
import com.wlanl.wx.entity.Realtime;
import com.wlanl.wx.properties.GirlFriendProperties;
import com.wlanl.wx.properties.WeCharMessageModelProperties;
import com.wlanl.wx.properties.WeatherProperties;
import com.wlanl.wx.service.WeCharService;
import com.wlanl.wx.utils.JsonUtils;
import com.wlanl.wx.utils.ThirdPartUtil;
import com.wlanl.wx.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * WeChar 业务处理层
 * @author wlanL
 */
@Service("weCharServiceImpl")
public class WeCharServiceImpl implements WeCharService {

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TOKEN =
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    @Value("classpath:messageModel.json")
    private Resource resource;

    @Autowired
    private GirlFriendProperties girlFriendProperties;

    @Autowired
    private WeCharMessageModelProperties weCharMessageModelProperties;

    @Autowired
    private WeatherProperties weatherProperties;

    @Override
    public void pushMessage(String city) {
        try {
            String accessToken = TokenUtil.getAccessToken();
            String url = TOKEN + accessToken;

            // 获取天气第三方数据
            String weatherUrl = weatherProperties.getWeatherPrefix()+city+weatherProperties.getWeatherSuffix();

            // 封装 当天消息
            Realtime weather = ThirdPartUtil
                    .getWeather(weatherUrl);

            // 获取 发送模板消息时 发送的本地data数据
            String json = JsonUtils.getJson(resource);

            Map<String, Object> jsonToMap = JsonUtils.getJsonToMap(json);

            // 消息模板的接收者
            jsonToMap.put("touser",weCharMessageModelProperties.getToUser());

            Map<String, MsgDataModel> data = (Map<String, MsgDataModel>)jsonToMap.get("data");

            // 刷新天气
            flushWeather(data,weather);

            // 填充其余数据
            fillNothingWeather(data,city);
            String jsonData = JSON.toJSONString(jsonToMap);

            // 发送模板消息
            String s = TokenUtil.postModelMsg(url, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 填充非天气的属性信息
     * @param data
     * @param city
     */
    private void fillNothingWeather(Map<String, MsgDataModel> data,String city) {
        Date date = new Date();

        // 实时时间
        MsgDataModel currentTime = data.get("currentTime");
        data.put("currentTime",new MsgDataModel(new SimpleDateFormat("yyyy-MM-dd E").format(new Date()),currentTime.getColor()));
        data.put("city",new MsgDataModel(city,"#173177"));

        // 恒温
        String temp = data.get("temperature").getValue();
        MsgDataModel temperature = data.get("temperature");
        int parseInt = Integer.parseInt(temperature.getValue());
        if (parseInt < 16) {
            data.put("temperature",new MsgDataModel(temperature.getValue() +"°C","#71c9ce"));
        }else if(parseInt < 24){
            data.put("temperature",new MsgDataModel(temperature.getValue() +"°C","#30e3ca"));
        }else{
            data.put("temperature",new MsgDataModel(temperature.getValue() +"°C","#b83b5e"));
        }

        // 生日
        long birNum = ThirdPartUtil.getBirthday(girlFriendProperties.getBirthday());
        MsgDataModel birthday = data.get("birthday");
        data.put("birthday",new MsgDataModel(String.valueOf(birNum),birthday.getColor()));

        // 恋爱天数
        MsgDataModel loveDay = data.get("love_day");
        Date parse = null;
        try {
            // 将相遇时间 变为毫秒
            parse = new SimpleDateFormat(TIME_FORMAT).parse(girlFriendProperties.getMeetTheTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int loveNum = ThirdPartUtil.differentDays(parse,date);
        data.put("love_day",new MsgDataModel(String.valueOf(loveNum),loveDay.getColor()));

        // 关心语句
        MsgDataModel info = data.get("info");
        MsgDataModel careBox = data.get("care_box");
        String care = ThirdPartUtil.randomCareBox(new Date(), temp, info.getValue());
        data.put("care_box",new MsgDataModel(care,careBox.getColor()));
    }

    /**
     * 刷新天气  拷贝到自己的实体中去
     * @param data 第三方天气数据
     * @param weather 映射第三方天气实体
     */
    private void flushWeather(Map<String, MsgDataModel> data, Realtime weather) {
        try {
            Class<?> aClass = Class.forName("com.wlanl.wx.entity.Realtime");
            Field[] declaredFields = aClass.getDeclaredFields();
            String color;
            for (Field declaredField : declaredFields) {
                switch (declaredField.getName()){
                    case "aqi":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getAqi(),color));
                        break;
                    case "temperature":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getTemperature(),color));
                        break;
                    case "direct":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getDirect(),color));
                        break;
                    case "humidity":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getHumidity(),color));
                        break;
                    case "power":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getPower(),color));
                        break;
                    case "info":
                        color = data.get(declaredField.getName()).getColor();
                        data.put(declaredField.getName(),new MsgDataModel(weather.getInfo(),color));
                        break;
                    default:
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
