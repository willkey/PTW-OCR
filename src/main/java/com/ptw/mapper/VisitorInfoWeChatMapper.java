package com.ptw.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ptw.pojo.VisitorInfoWeChat;
/**
 * 证照系统小程序相关
 * @author hanwei
 *
 */
@Mapper
public interface VisitorInfoWeChatMapper extends BaseMapper<VisitorInfoWeChat>{
	public List<VisitorInfoWeChat> getVisitorByOrderId(
			@Param("orderId")String orderId ,
			@Param("visitorName")String visitorName ,
			@Param("mobilePhone")String mobilePhone);

}
