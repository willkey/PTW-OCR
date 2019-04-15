package com.ptw.service.impl;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baidu.aip.ocr.AipOcr;
import com.ptw.pojo.IdCard;
import com.ptw.service.OCRService;
import com.ptw.utils.PTWResult;
import com.ptw.utils.PtwContracts;
@Service
public class OCRServiceImpl implements OCRService {
	AipOcr client = new AipOcr(PtwContracts.BAIDU_APP_ID, PtwContracts.BAIDU_API_KEY, PtwContracts.BAIDU_SECRET_KEY);
	@Override
	public PTWResult idcard(String imagePath, String type) {//type:front正面
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = client.idcard(imagePath, type, new HashMap<String, String>());	//front前面
        JSONObject obj = res.getJSONObject("words_result");
        IdCard idCard = new IdCard();
        idCard.setName(obj.getJSONObject("姓名").getString("words"));
        idCard.setNation(obj.getJSONObject("民族").getString("words"));
        idCard.setAddress(obj.getJSONObject("住址").getString("words"));
        idCard.setIdNo(obj.getJSONObject("公民身份号码").getString("words"));
        idCard.setBirthday(obj.getJSONObject("出生").getString("words"));
        idCard.setSex(obj.getJSONObject("性别").getString("words"));
        /**
         *  normal-识别正常
			reversed_side-未摆正身份证
			non_idcard-上传的图片中不包含身份证
			blurred-身份证模糊
			over_exposure-身份证关键字段反光或过曝
			unknown-未知状态
         */
        String status = res.getString("image_status");
        if(!StringUtils.equals(status, "normal")) {//不正常
        	if(StringUtils.equals(status, "reversed_side")) {
        		return PTWResult.build(500, "未摆正身份证");
        	}else if(StringUtils.equals(status, "non_idcard")) {
        		return PTWResult.build(500, "上传的图片中不包含身份证");
        	}else if(StringUtils.equals(status, "blurred")) {
        		return PTWResult.build(500, "身份证模糊");
        	}else if(StringUtils.equals(status, "over_exposure")) {
        		return PTWResult.build(500, "身份证关键字段反光或过曝");
        	}else if(StringUtils.equals(status, "unknown")) {
        		return PTWResult.build(500, "上传的图片中不包含身份证");
        	}
        }
		return PTWResult.ok(idCard);
	}

}
