package com.ptw.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baidu.aip.ocr.AipOcr;
import com.ptw.pojo.IdCard;
import com.ptw.pojo.IdCardback;
import com.ptw.pojo.passport;
import com.ptw.service.OCRService;
import com.ptw.utils.PTWResult;
import com.ptw.utils.PtwContracts;

@Service
public class OCRServiceImpl implements OCRService {
	AipOcr client = new AipOcr(PtwContracts.BAIDU_APP_ID, PtwContracts.BAIDU_API_KEY, PtwContracts.BAIDU_SECRET_KEY);
	static Map<String, String> map = new HashMap<String, String>();
	static {

		map.put("JAN", "01");
		map.put("FEB", "02");
		map.put("MAR", "03");
		map.put("APR", "04");
		map.put("MAY", "05");
		map.put("JUN", "06");
		map.put("JUL", "07");
		map.put("AUG", "08");
		map.put("SEP", "09");
		map.put("OCT", "10");
		map.put("NOV", "11");
		map.put("DEC", "12");
	}

	@Override
	public PTWResult idcard(String imagePath, String type) {// type:front正面
															// type:back反面
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		JSONObject res = client.idcard(imagePath, type, null); // front前面

		/**
		 * normal-识别正常 reversed_side-未摆正身份证 non_idcard-上传的图片中不包含身份证
		 * blurred-身份证模糊 over_exposure-身份证关键字段反光或过曝 unknown-未知状态
		 */
		String status = res.getString("image_status");
		if (!StringUtils.equals(status, "normal")) {// 不正常
			if (StringUtils.equals(status, "reversed_side")) {
				return PTWResult.build(500, "未摆正身份证");
			} else if (StringUtils.equals(status, "non_idcard")) {
				return PTWResult.build(500, "上传的图片中不包含身份证");
			} else if (StringUtils.equals(status, "blurred")) {
				return PTWResult.build(500, "身份证模糊");
			} else if (StringUtils.equals(status, "over_exposure")) {
				return PTWResult.build(500, "身份证关键字段反光或过曝");
			} else if (StringUtils.equals(status, "unknown")) {
				return PTWResult.build(500, "上传的图片中不包含身份证");
			}
		}
		/**
		 * 正常根据身份证类型赋值
		 */
		JSONObject obj = res.getJSONObject("words_result");
		if ("front".equals(type)) {
			IdCard idCard = new IdCard();
			idCard.setName(obj.getJSONObject("姓名").getString("words"));
			idCard.setNation(obj.getJSONObject("民族").getString("words"));
			idCard.setAddress(obj.getJSONObject("住址").getString("words"));
			idCard.setIdNo(obj.getJSONObject("公民身份号码").getString("words"));
			idCard.setBirthday(obj.getJSONObject("出生").getString("words"));
			idCard.setSex(obj.getJSONObject("性别").getString("words"));
			return PTWResult.ok(idCard);
		} else {
			IdCardback idCardback = new IdCardback();
			idCardback.setStartDate(obj.getJSONObject("签发日期").getString("words"));
			idCardback.setAuthority(obj.getJSONObject("签发机关").getString("words"));
			idCardback.setEndDate(obj.getJSONObject("失效日期").getString("words"));
			return PTWResult.ok(idCardback);
		}

	}

	@Override
	public PTWResult passport(String imagePath) {
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		JSONObject res = client.passport(imagePath, null);
		System.err.println(res);
		/*
		 * 错误信息
		 */
		String status = null;
		try {
			status = res.getString("error_msg");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if ("target detect error".equals(status)) {
			return PTWResult.build(500, "未检测到护照");
		}
		/**
		 * 当识别正确时返回对象
		 */
		JSONObject obj = res.getJSONObject("words_result");
		passport passport = new passport();
		/**
		 * private String name;//姓名 private String issuePlace;//护照签发地点 private
		 * String PassportNumber;//护照号码 private String issueDate;//签发日期 private
		 * String birthPlace;//出生地点 private String birthday;//生日 private String
		 * countryCode;//国家码 private String valid;//有效期至 private String
		 * englishName;//姓名拼音 private String sex;//性别
		 */
		
		// 先处理信息
		try {
			passport.setIssueDate(Dateconversion(obj.getJSONObject("签发日期").getString("words")));
			passport.setBirthday(DateBirthday(obj.getJSONObject("生日").getString("words")));
			passport.setValid(Dateconversion(obj.getJSONObject("有效期至").getString("words")));
		} catch (Exception e) {
			// TODO: handle exception
			passport.setIssueDate("");
			passport.setBirthday("");
			passport.setValid("");
		}
		// TODO Auto-generated method stub
		// 无异常接着处理
		passport.setName(obj.getJSONObject("姓名").getString("words"));
		passport.setIssuePlace(obj.getJSONObject("护照签发地点").getString("words"));
		passport.setPassportNumber(obj.getJSONObject("护照号码").getString("words"));
		passport.setBirthPlace(obj.getJSONObject("出生地点").getString("words"));
		passport.setCountryCode(obj.getJSONObject("国家码").getString("words"));
		passport.setEnglishName(obj.getJSONObject("姓名拼音").getString("words"));
		passport.setSex(obj.getJSONObject("性别").getString("words"));
		return PTWResult.ok(passport);
	}

	/**
	 * 日期转换 057月/JUL201638 转 2016-07-05
	 * 
	 */
	private static String Dateconversion(String date) {
		String year = null;
		String month = null;
		String day = date.substring(0, 2);

		for (String key : map.keySet()) {
			if (date.contains(key)) {
				month = map.get(key);
				year = date.substring(date.indexOf(key) + 3, date.indexOf(key) + 7);
			}
		}
		String data = year + "-" + month + "-" + day;
		return data;
	}

	/**
	 * 出生日期转换
	 * 
	 */
	private static String DateBirthday(String date) {
		String year = null;
		String month = null;
		String day = null;

		for (String key : map.keySet()) {
			if (date.contains(key)) {
				month = map.get(key);
				year = date.substring(date.indexOf(key) + 3, date.indexOf(key) + 7);
				day = date.substring(0, date.indexOf(key));
			}
		}
		String data = year + "-" + month + "-" + day;
		return data;
	}
}
