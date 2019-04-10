package com.ptw;

import org.json.JSONObject;
import org.junit.Test;

import com.baidu.aip.speech.AipSpeech;

public class TestYuxin {
	//设置APPID/AK/SK
    public static final String APP_ID = "15731769";
    public static final String API_KEY = "jcgVOXXTIKcgDyFm5lRXWDEB";
    public static final String SECRET_KEY = "2aZhvM1OTdWvlNpZt1QNQGQNaifiuUs5";
    @Test
	public void t1() {
    	// 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        JSONObject res = client.asr("E:\\1private\\1548391665157 00_11_00-00_14_00.mp3", "mp3", 16000, null);
        System.out.println(res.toString(2));
    }
}
