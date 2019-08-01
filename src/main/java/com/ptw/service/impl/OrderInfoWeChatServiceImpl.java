package com.ptw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ptw.mapper.OrderInfoWeChatMapper;
import com.ptw.pojo.OrderInfoWeChat;
import com.ptw.service.OrderInfoWeChatService;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
@Service
public class OrderInfoWeChatServiceImpl extends ServiceImpl<OrderInfoWeChatMapper,OrderInfoWeChat> implements OrderInfoWeChatService{

	@Override
	public List<OrderInfoWeChat> getOrderByMobile(String mobile ,String projectCode) {
		return this.baseMapper.getOrderByMobile(mobile ,projectCode);
	}

}
