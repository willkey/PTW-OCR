package com.ptw.service;

import com.ptw.utils.PTWResult;

public interface OCRService {
	public PTWResult idcard(String imagePath,String type);

	public PTWResult passport(String imagePath);
}
