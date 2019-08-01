package com.ptw.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ptw.pojo.OrderInfoWeChat;
import com.ptw.pojo.UpdateVisitorCredentialWeChat;
import com.ptw.pojo.UpdateVisitorWeChat;
import com.ptw.pojo.VisitorInfoWeChat;
import com.ptw.service.OrderInfoWeChatService;
import com.ptw.service.UpdateVisitorCredentialWeChatService;
import com.ptw.service.UpdateVisitorWeChatService;
import com.ptw.service.VisitorInfoWeChatService;
import com.ptw.utils.PTWResult;

import lombok.extern.slf4j.Slf4j;
/**
 * 小程序证照系统数据接口
 * @author user
 *
 */
@Controller
@RequestMapping("/wx_papers")
@Slf4j
public class IndexController {
	@Autowired
	private OrderInfoWeChatService oiwcService;
	@Autowired
	private VisitorInfoWeChatService viwService;
	@Autowired
	private UpdateVisitorWeChatService uvwcService;
	@Autowired
	private UpdateVisitorCredentialWeChatService uvcwcService;
	
	@RequestMapping("/index")
	@ResponseBody
	public String tt() {
		return "aaaaaaaaaaaaaaaaa";
	}
	
	/**
	 * 1.根据手机号查询订单信息
	 * 以及订单里面的个人信息以及证照信息（没护照或者身份证的需要单独拿出来）
	 * num=0：返回没有证照信息的数据
	 * num=1：返回证照信息不全的数据
	 * num=3：所有数据按照最新时间顺序排列
	 */
	@RequestMapping(value="/get_order_by_mobile",method=RequestMethod.POST)
	@ResponseBody
	public PTWResult getOrderByMobile(
			@RequestParam(required = true,value = "mobile")String mobile,
			@RequestParam(required = true,value = "num")Integer num,
			@RequestParam(value = "likeParam",required = false)String likeParam) {
		if(mobile==null||mobile.equals("")||mobile.equals("undefined")) {
			return PTWResult.build(500, "手机号有误！");
		}
		log.info("正在请求订单游客证件信息...");
		List<OrderInfoWeChat> lists = getOrderAndVisitor(mobile,likeParam);
		List<OrderInfoWeChat> lists1 = new ArrayList<OrderInfoWeChat>();
		if(num==0) {
			for(OrderInfoWeChat list:lists) {
				if(list.getVisitorStatus().equals("未录入")) {
					lists1.add(list);
				}
			}
		}else if(num==1) {
			for(OrderInfoWeChat list:lists) {
				if(list.getVisitorStatus().equals("待录入")) {
					//System.out.println("VisitorStatus:"+list.getVisitorStatus());
					lists1.add(list);
				}
			}
		}else if(num==3) {
			lists1=lists; 
		}
		/*for(OrderInfoWeChat lsit:lists1) {
			System.out.println(lsit.toString());
		}*/
		log.info("订单游客信息证件请求完毕！");
		return PTWResult.ok(lists1);
	}
	public List<OrderInfoWeChat>  getOrderAndVisitor(String mobile ,String likeParam) {
		String projectCode = null;
		String visitorName = null;
		String mobilePhone = null;
		if(isInteger(likeParam)) {
			mobilePhone = likeParam;
			System.out.println("游客手机号："+mobilePhone);
		}else if(likeParam.matches("[\u4E00-\u9FA5]+")) {
			visitorName = likeParam;
			System.out.println("游客姓名："+visitorName);
		}else {
			projectCode = likeParam;
			System.out.println("团号是："+projectCode);
		}
		
		List<OrderInfoWeChat> lists = oiwcService.getOrderByMobile(mobile ,projectCode);
		List<VisitorInfoWeChat> viws; 
		for(OrderInfoWeChat list:lists) {
			//System.out.println("订单id："+list.getOrderId());
			viws = viwService.getVisitorByOrderId(list.getOrderId() ,visitorName ,mobilePhone);
			for(VisitorInfoWeChat viw:viws) {
				/*System.out.println("游客姓名："+viw.getVisitorName());
				System.out.println("游客手机号："+viw.getMobilePhone());
				System.out.println("游客护照："+viw.getPassport());
				System.out.println("游客身份证："+viw.getIdCard());*/
				if(
						viw.getVisitorName()==null&&viw.getVisitorName().equals("")&&
						viw.getMobilePhone()==null&&viw.getMobilePhone().equals("")&&
						viw.getPassport()==null&&viw.getPassport().equals("")&&viw.getPassport().equals("null")&&
						viw.getIdCard()==null&&viw.getIdCard().equals("")) {
					list.setVisitorStatus("未录入"); //如果游客信息都为空 则未录入
					break;
				}else if(
						viw.getVisitorName()!=null&&!viw.getVisitorName().equals("")&&
						viw.getMobilePhone()!=null&&!viw.getMobilePhone().equals("")&&
						viw.getPassport()!=null&&!viw.getPassport().equals("")&&!viw.getPassport().equals("null")&&
						viw.getIdCard()!=null&&!viw.getIdCard().equals("")){
					list.setVisitorStatus("已录入"); //如果游客信息都不为空，代表已录入
				}else {
					list.setVisitorStatus("待录入"); //如果游客信息部分为空 则待录入 
				}
			}
			list.setCreateDate(list.getCreateDate().substring(0, 16));
			list.setViws(viws);
		}
		Iterator<OrderInfoWeChat> it = lists.iterator();
		/**
		 * 移除游客信息为空的订单
		 * 用于模糊查询
		 */
		while(it.hasNext()) {
			if(it.next().getViws().isEmpty()) {
				it.remove();
			}
		}
		return lists;
	}
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	
	
	/**
	 * 2.添加游客证件信息
	 * @RequestParam(value=”参数名”,required=”true/false”,defaultValue=””)
	 * value：参数名
	 * required：是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
	 * defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
	 */
	@RequestMapping(value="/update_visitor_info",method=RequestMethod.POST)
	@ResponseBody
	public PTWResult updateVisitorInfo(
			@RequestParam(value = "ids",required = false)String ids,
			@RequestParam(value = "list",required = false)String list,
			@RequestParam(value = "list1",required = false)String list1,
			@RequestParam(value = "phoneNum",required = false)String phoneNum,
			@RequestParam(value = "list2",required = false)String list2) {
		UpdateVisitorWeChat uvwc = new UpdateVisitorWeChat();
		UpdateVisitorCredentialWeChat uvcwc = new UpdateVisitorCredentialWeChat();
		System.out.println("ids:"+ids);
		String s1 = ids.replace("\\", "");  
        String s2 = s1.replace("{\"ids\":\"", "");  
        String s3 = s2.replace("\"}", ""); 
        String s4 = s3+"\"}";
        System.out.println("s4:"+s4);
		if(ids!=null&&list!=null&&list1!=null) {
			log.info("添加身份证信息...");
			if(phoneNum==null||phoneNum.equals("")||phoneNum.equals("undefined")) {
				return PTWResult.build(500, "手机号有误！");
			}
			
			JSONObject jsonId = JSONObject.parseObject(s4);
			JSONObject json = JSONObject.parseObject(list);
			JSONObject json1 = JSONObject.parseObject(list1);
			uvwc.setOrderVisitorId(jsonId.getString("ordervisitorid"));
			uvwc.setVisitorName(json.getString("name"));
			if(json.getString("sex").equals("女")) {
				uvwc.setGender(0);
			}else {
				uvwc.setGender(1);
			}
			uvwc.setGenderName(json.getString("sex"));
			uvwc.setBirthday(json.getString("birthday"));
			uvwc.setBirthplace(json.getString("address"));
			uvwc.setMobilePhone(phoneNum);
			uvwc.setNation(json.getString("nation"));
			
			uvcwc.setOrderVisitorCredentialId(jsonId.getString("idcardid"));
			uvcwc.setOrderVisitorId(jsonId.getString("ordervisitorid"));
			uvcwc.setCredentialType(1);
			uvcwc.setCredentialTypeName("身份证");
			uvcwc.setCredentialCode(json.getString("idNo"));
			uvcwc.setCredentialOrgan(json1.getString("authority"));
			uvcwc.setCredentialSignDate(json1.getString("startDate"));
			uvcwc.setCredentialValidity(json1.getString("endDate"));
			uvwcService.updateById(uvwc);
			uvcwcService.insertOrUpdate(uvcwc);
			log.info("身份证信息添加成功！");
		}else if(ids!=null&&list2!=null) {
			log.info("添加护照信息...");
			JSONObject jsonId = JSONObject.parseObject(s4);
			JSONObject json2 = JSONObject.parseObject(list2);
			uvcwc.setOrderVisitorCredentialId(jsonId.getString("passportid"));
			uvcwc.setOrderVisitorId(jsonId.getString("ordervisitorid"));
			uvcwc.setCredentialType(2);
			uvcwc.setCredentialTypeName("护照");
			uvcwc.setCredentialArea(json2.getString("issuePlace"));
			uvcwc.setCredentialCode(json2.getString("passportNumber"));
			uvcwc.setCredentialSignDate(json2.getString("passportNumber"));
			uvcwc.setCredentialValidity(json2.getString("valid"));
			System.out.println(uvcwc.toString());
			uvcwcService.insertOrUpdate(uvcwc);
			log.info("护照信息添加成功！");
		}
		return PTWResult.ok();
	}
	
	
	/**
	 * 3.根据id验证游客身份证信息是否存在
	 */
	@RequestMapping(value="/get_visitor_by_id",method=RequestMethod.POST)
	@ResponseBody
	public PTWResult getVisitorById(
			@RequestParam(value = "idcardid",required = false)String idcardid,
			@RequestParam(value = "orderVisitorId",required = false)String orderVisitorId) {
		//System.out.println(orderVisitorId);
		UpdateVisitorWeChat uvwc = uvwcService.selectById(orderVisitorId);
		UpdateVisitorCredentialWeChat uvcwc = uvcwcService.selectById(idcardid);
		if(uvwc!=null&&uvcwc!=null&&uvwc.getVisitorName()!=null&&uvwc.getGenderName()!=null&&
				uvwc.getBirthday()!=null&&uvwc.getBirthplace()!=null&&uvwc.getMobilePhone()!=null&&uvwc.getNation()!=null&&
				uvcwc.getOrderVisitorCredentialId()!=null&&uvcwc.getCredentialType()!=null&&
				uvcwc.getCredentialTypeName()!=null&&uvcwc.getCredentialCode()!=null&&
				uvcwc.getCredentialOrgan()!=null&&uvcwc.getCredentialSignDate()!=null&&
				uvcwc.getCredentialValidity()!=null) {
			return PTWResult.build(300,"身份证信息已存在！");
		}
		return PTWResult.ok();
	}
	
}
