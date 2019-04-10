package com.ptw;

import java.util.HashMap;

import org.json.JSONObject;
import org.junit.Test;

import com.baidu.aip.ocr.AipOcr;

public class First {
	//设置APPID/AK/SK
    public static final String APP_ID = "15731769";
    public static final String API_KEY = "jcgVOXXTIKcgDyFm5lRXWDEB";
    public static final String SECRET_KEY = "2aZhvM1OTdWvlNpZt1QNQGQNaifiuUs5";
    /**
     * 身份证-正面照
     */
    @Test
	public void t0() {
    	// 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String path = "C:\\Users\\fh\\Desktop\\test.jpg";
        JSONObject res = client.idcard(path, "front", new HashMap<String, String>());	//front前面
        System.out.println(res.toString(2));
	}
    /**
     * 身份证-背面照
     */
    @Test
	public void t1() {
    	// 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String path = "C:\\Users\\fh\\Desktop\\test2.jpg";
        JSONObject res = client.idcard(path, "back", new HashMap<String, String>());	//front前面
        System.out.println(res.toString(2));
	}
    
    /***
     * 护照识别
     */
    @Test
	public void t2() {
    	// 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String path = "C:\\Users\\fh\\Desktop\\findMongoImg.png";
        JSONObject res = client.passport(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
	}
}
