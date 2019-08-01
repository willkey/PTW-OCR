package com.ptw.pojo;

import lombok.Data;

/**
 * 证照系统查询专用实体类
 * @author hanwei
 *
 */
@Data
public class VisitorInfoWeChat {
	
	private String orderVisitorId; //游客id
	private String visitorName; //姓名
	private String mobilePhone; //手机号
	private String passportID; 
	private String passport;
	private String idCardID;
	private String idCard;

}
