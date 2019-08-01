package com.ptw.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
/**
 * 证照系统更新游客信息专用实体类
 * @author HanWei
 *
 */
@TableName("OrderVisitor")
@Data
public class UpdateVisitorWeChat {
	
	@TableId("OrderVisitorId")
	private String orderVisitorId; //游客id
	@TableField("VisitorName")
	private String visitorName; //游客中文名
	@TableField("Gender")
	private Integer gender;  //性别性别(1男 0女)
	@TableField("GenderName")
	private String GenderName; //性别名称
	@TableField("Birthday")
	private String Birthday; //出生日期
	@TableField("Birthplace")
	private String Birthplace; //出生地点
	@TableField("MobilePhone")
	private String mobilePhone; // 手机号
	@TableField("Nation")
	private String nation;  //民族

}
