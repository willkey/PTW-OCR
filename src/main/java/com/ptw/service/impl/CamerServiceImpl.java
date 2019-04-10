package com.ptw.service.impl;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.baidu.aip.ocr.AipOcr;
import com.ptw.service.CameraService;
import com.ptw.utils.PTWResult;
import com.ptw.utils.PtwContracts;
@Service
public class CamerServiceImpl implements CameraService {
	AipOcr client = new AipOcr(PtwContracts.BAIDU_APP_ID, PtwContracts.BAIDU_API_KEY, PtwContracts.BAIDU_SECRET_KEY);
	@Override
	public PTWResult idCard_front() {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String path = "C:\\Users\\fh\\Desktop\\test1.jpg";
        JSONObject res = client.idcard(path, "front", new HashMap<String, String>());	//front前面
//        JSONObject res = client.general(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
		return null;
	}

	@Override
	public PTWResult idCard_behind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PTWResult passport() {
		// TODO Auto-generated method stub
		return null;
	}

}
