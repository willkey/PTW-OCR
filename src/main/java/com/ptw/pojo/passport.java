package com.ptw.pojo;

import lombok.Data;
/*
 * {"log_id":3423160133572841656,
"words_result":{
"姓名":{"words":"郭宝","location":{"top":239,"left":497,"width":114,"height":44}},
"护照签发地点":{"words":"北京/BEIJING","location":{"top":605,"left":504,"width":260,"height":35}},
"护照号码":{"words":"E28044986","location":{"top":130,"left":1027,"width":326,"height":46}},
"签发日期":{"words":"118月/AUG2014Y","location":{"top":493,"left":988,"width":331,"height":38}},
"出生地点":{"words":"陕西/SHAANXI","location":{"top":504,"left":492,"width":264,"height":36}},
"生日":{"words":"26OCT1978","location":{"top":393,"left":982,"width":254,"height":45}},
"国家码":{"words":"CHN","location":{"top":137,"left":705,"width":90,"height":29}},
"有效期至":{"words":"108月/AUG2024","location":{"top":596,"left":989,"width":328,"height":35}},
"姓名拼音":{"words":"GUOBAO","location":{"top":291,"left":495,"width":190,"height":30}},
"性别":{"words":"男/M","location":{"top":398,"left":496,"width":80,"height":43}}},"words_result_num":10}
 * */
@Data
public class passport {
	private String name;//姓名
	private String issuePlace;//护照签发地点
	private String passportNumber;//护照号码
	private String issueDate;//签发日期
	private String birthPlace;//出生地点
	private String birthday;//生日
	private String countryCode;//国家码
	private String valid;//有效期至
	private String englishName;//姓名拼音
	private String sex;//性别
}
