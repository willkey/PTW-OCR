package com.ptw.pojo;

import lombok.Data;

@Data
public class IdCardback {
	private String Authority;// 签发机关
	private String startDate;// 签发日期
	private String endDate;// 失效日期
}
