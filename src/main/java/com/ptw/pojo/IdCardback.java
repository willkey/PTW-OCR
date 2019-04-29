package com.ptw.pojo;

import lombok.Data;

@Data
public class IdCardback {
	private String authority;// 签发机关
	private String startDate;// 签发日期
	private String endDate;// 失效日期
}
