package com.ptw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ptw.mapper.VisitorInfoWeChatMapper;
import com.ptw.pojo.VisitorInfoWeChat;
import com.ptw.service.VisitorInfoWeChatService;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
@Service
public class VisitorInfoWeChatServiceImpl extends ServiceImpl<VisitorInfoWeChatMapper, VisitorInfoWeChat> implements VisitorInfoWeChatService{

	@Override
	public List<VisitorInfoWeChat> getVisitorByOrderId(String orderId , String visitorName,String mobilePhone) {
		return this.baseMapper.getVisitorByOrderId(orderId ,visitorName ,mobilePhone);
	}

}
