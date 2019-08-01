package com.ptw.service;

import java.util.List;


import com.baomidou.mybatisplus.service.IService;
import com.ptw.pojo.OrderInfoWeChat;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
public interface OrderInfoWeChatService extends IService<OrderInfoWeChat>{
	public List<OrderInfoWeChat> getOrderByMobile(String mobile ,String projectCode);

}
