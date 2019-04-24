package com.ptw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptw.service.OCRService;
import com.ptw.utils.PTWResult;
import com.ptw.utils.SystemParams;

@Controller
public class OCRController {
	@Autowired
	private SystemParams params;
	@Autowired
	private OCRService ocrService;
	@RequestMapping("/idcard")
	@ResponseBody
	public PTWResult idcard(String imagePath,String type) {
		String fullPath = params.getImagePath()+imagePath;
		return ocrService.idcard(fullPath, type);
	}

	@RequestMapping("/passport")
	@ResponseBody
	public PTWResult passport(String imagePath) {
		String fullPath = params.getImagePath()+imagePath;
		return ocrService.passport(fullPath);
	}}
