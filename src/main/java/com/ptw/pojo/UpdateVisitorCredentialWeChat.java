package com.ptw.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 证照系统更新游客证件专用实体类
 * @author HanWei
 *
 */
@TableName("OrderVisitorCredential")
@Data
public class UpdateVisitorCredentialWeChat {
					
	@TableId(value="OrderVisitorCredentialId" ,type = IdType.UUID)	
	private String orderVisitorCredentialId; //游客证件id
	@TableField("OrderVisitorId")
	private String orderVisitorId; //游客id
	@TableField("CredentialType")	
	private Integer credentialType; //证件类型1:身份证，2:护照			
	@TableField("CredentialTypeName")				
	private String credentialTypeName; //证件类型说明：身份证，护照
	@TableField("CredentialArea")
	private String credentialArea; //签发地点  --护照专有
	@TableField("CredentialCode")
	private String credentialCode; //身份证号  |护照号
	@TableField("CredentialOrgan")
	private String credentialOrgan;  //签发机关|派出所
	@TableField("CredentialSignDate")
	private String credentialSignDate; //办理时间|签发时间
	@TableField("CredentialValidity")
	private String credentialValidity;  //到期时间|有效期

}
