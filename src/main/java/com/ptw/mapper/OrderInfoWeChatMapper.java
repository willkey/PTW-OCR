package com.ptw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ptw.pojo.OrderInfoWeChat;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
@Mapper
public interface OrderInfoWeChatMapper extends BaseMapper<OrderInfoWeChat>{
	public List<OrderInfoWeChat> getOrderByMobile(@Param("mobile")String mobile ,@Param("projectCode")String projectCode);

}
