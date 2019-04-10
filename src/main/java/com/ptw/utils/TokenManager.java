package com.ptw.utils;
import java.util.Date;
import java.util.Properties;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class TokenManager {
	public static AccessToken accessToken;

	public static String getToKen() {

		long nowTime = System.currentTimeMillis();
		try {
			if (accessToken == null) {
				log.info("accessToken == null");
				accessToken = getAccessToken();
			} else if ((nowTime - accessToken.getCreateTime() + 10000) >= accessToken.getExpiresIn()) {
				log.info("nowTime:"+PtwContracts.DATE_YMDHMS.format(new Date(nowTime))+
						"\t createTime:"+PtwContracts.DATE_YMDHMS.format(new Date(accessToken.getCreateTime()))+
						"\t +10000:"+(nowTime - accessToken.getCreateTime() + 10000)+
						"\t expiresIn:"+accessToken.getExpiresIn());
				accessToken = getAccessToken();
			}else {
				log.info("===look===nowTime:"+PtwContracts.DATE_YMDHMS.format(new Date(nowTime))+
						"\t createTime:"+PtwContracts.DATE_YMDHMS.format(new Date(accessToken.getCreateTime()))+
						"\t +10000:"+(nowTime - accessToken.getCreateTime() + 10000)+
						"\t expiresIn:"+accessToken.getExpiresIn());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken.getAppKey();
	}
	private static AccessToken getAccessToken() {
		log.info("===getAccessToken()===");
		Properties prop = PropUtil.getProperties("/config.properties");
		String url = prop.getProperty("TOKERN_URL");
		String appid=prop.getProperty("APP_ID");
		String APPSECRET=prop.getProperty("APPSECRET");
		url = url.replace("{appid}", appid);
		url = url.replace("{secret}", APPSECRET);
		String str = null;
		try {
			str = HttpUtil.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(str);
		System.out.println(jsonObject.toJSONString());
		AccessToken token = new AccessToken();
		token.setExpiresIn(jsonObject.getLongValue("expires_in")*1000);//超时时间，2小时：7200s,*1000是为了转换为毫秒
		token.setCreateTime(System.currentTimeMillis());
		token.setAppKey(jsonObject.getString("access_token"));
		return token;
	}

	public static class AccessToken {

//		private String id;

		private String appKey;

		private long createTime;

		private long expiresIn;
		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public long getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(long expiresIn) {
			this.expiresIn = expiresIn;
		}

	}
}
