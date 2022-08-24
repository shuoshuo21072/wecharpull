package com.wlanl.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wlanl.wx.entity.Realtime;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 第三方服务接口
 * @author wlanL
 */
@Component
public class ThirdPartUtil {

    /**
     * 获取天气情况
     * @param weatherUrl 第三方url
     * @return data
     */
    public static Realtime getWeather(String weatherUrl){
        String info = TokenUtil.get(weatherUrl);
        System.out.println(info);
        Map<String, String> smdm = JSON.parseObject(info, new TypeReference<Map<String, String>>() {});
        String result = smdm.get("result");
        Map<String, String> map = JSON.parseObject(result, new TypeReference<Map<String, String>>() {});
        if (map != null){
            String realtime = map.get("realtime");
            return JSON.parseObject(realtime, new TypeReference<Realtime>() {});
        }
        // 这里要想请求不到数据还可以返回天气数据，就把这行注释放开，但是数据是死的
        // return new Realtime("15","86","晴","北风","≤3","32");
        throw new RuntimeException("展示不支持该城市...");
    }

    /**
     * 触发时间
     */
    public static final int EX_TIME = 7;
    /**
     * 最低温度
     */
    public static final int CODE_TEMP = 16;
    /**
     * 触发（下雨天推送暖心句子）关键字
     */
    public static final char RAIN_KEYWORD = '雨';
    /**
     * 获取暖心句子
     * @param date 推送消息时候的日期
     * @param temperature 温度
     * @param info 天气情况
     * @return String.class
     */
    public static String randomCareBox(Date date,String temperature, String info){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentTime = cal.get(Calendar.HOUR_OF_DAY);
        System.out.println(currentTime);
        // 7点前发送消息应从早安消息中发送
        if (currentTime < EX_TIME) {
            return RandomMessageUtils.CARE_MORNING_MESSAGES
                    [(int) (Math.random() * RandomMessageUtils.CARE_MORNING_MESSAGES.length-1)];
        }else if(Integer.parseInt(temperature) < CODE_TEMP){
            return RandomMessageUtils.CODE_MESSAGE
                    [(int) (Math.random() * RandomMessageUtils.CODE_MESSAGE.length-1)];
        }else if(info.indexOf(RAIN_KEYWORD) != -1){
            return RandomMessageUtils.RAIN_MESSAGE
                    [(int) (Math.random() * RandomMessageUtils.RAIN_MESSAGE.length-1)];
        }else{
            return RandomMessageUtils.AMB_MESSAGE
                    [(int) (Math.random() * RandomMessageUtils.AMB_MESSAGE.length-1)];
        }
    }

    /**
     * 获取距离下次生气时间差
     * @param day 那一天生日
     * @return 生日时间
     */
    public static long getBirthday(String day){
        Calendar instance = Calendar.getInstance();
        String string = (instance.getWeekYear() + 1) + "-" + day;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = simpleDateFormat.parse(string);
            return (date.getTime() - System.currentTimeMillis())/1000/60/60/24;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 两时间差  单位：/天
     * @param date1 起始时间
     * @param date2 当前时间
     * @return 时间差
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)
                {
                    timeDistance += 366;
                }
                else
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        } else {
            return day2-day1;
        }
    }
}
