package com.ptw.pojo;

import java.util.List;

import lombok.Data;

/**
 * 证照系统查询专用实体类
 * @author hanwei
 *
 */
@Data
public class OrderInfoWeChat {
	private String projectId; //团ID
	private String projectCode; //团编号
	private String createDate; //创建时间
	private Integer projectCloseStatus; //封团状态
	private String orderId; //订单ID
	/**
	 * 0，代表未录入
	 * 1，代表待录入
	 * 2，代表已录入
	 */
	private String visitorStatus; 
	private List<VisitorInfoWeChat> viws;

}
