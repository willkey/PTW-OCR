package com.ptw.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ptw.pojo.VisitorInfoWeChat;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
public interface VisitorInfoWeChatService extends IService<VisitorInfoWeChat>{
	List<VisitorInfoWeChat> getVisitorByOrderId(String orderId , String visitorName, String mobilePhone);
}
