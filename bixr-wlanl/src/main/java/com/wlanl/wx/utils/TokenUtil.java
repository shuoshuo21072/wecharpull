package com.wlanl.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wlanl.wx.entity.AccessToken;
import com.wlanl.wx.properties.WeCharMessageModelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author wlanL
 */
@Component
public class TokenUtil {

    public static WeCharMessageModelProperties weCharMessageModelProperties;

    @Autowired
    public void setWeCharMessageModelProperties(WeCharMessageModelProperties weCharMessageModelProperties) {
        TokenUtil.weCharMessageModelProperties = weCharMessageModelProperties;
    }

    private static AccessToken accessToken;

    /**
     * 获取 发送 获取token的url
     */
    private static String getTokenUrl(){
        if (weCharMessageModelProperties == null){
            throw new RuntimeException("token 失败...");
        }else{
            return weCharMessageModelProperties.getToken();
        }

    }

    /**
     * 通过url 获取一个token
     */
    private static void getToken(){
        try {
            String tokenUrl = getTokenUrl();
            String result = get(tokenUrl);
                Map<String, String> map = JSON.parseObject(result, new TypeReference<Map<String, String>>() {});
            String errcode = map.get("errcode");
            if (errcode == null || "0".equals(errcode)) {
                String token = map.get("access_token");
                long expiresIn = Long.parseLong(map.get("expires_in"));
                accessToken =  new AccessToken(token,expiresIn);
            }else{
                throw new RuntimeException("token 获取失败, 请查看{appId}以及{appSecret}是否正确...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个存在且不超时的token
     * @return token.string
     */
    public static String getAccessToken(){
        if (accessToken == null || accessToken.isExpires()) {
            getToken();
        }
        return accessToken.getAccessToken();
    }

    /**
     * 获取 模板信息
     * @param msgUrl 模板信息url
     * @return json串
     */
    public static String postModelMsg(String msgUrl,String data){
        // 207301
        return post(msgUrl, data);
    }

    /**
     * 获取链接结果的封装
     * @param url 链接
     * @return result
     */
    public static String get(String url){
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            InputStream is = urlConnection.getInputStream();
            int len;
            byte[] bytes = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = is.read(bytes)) != -1){
                stringBuilder.append(new String(bytes,0,len));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * post请求 参数携带
     * @param url 地址
     * @param data 参数
     */
    private static String post(String url,String data){
        URL u = null;
        try {
            u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.close();
            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
