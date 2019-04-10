package com.ptw.utils;

import java.text.SimpleDateFormat;

public class PtwContracts {
	public static final String BAIDU_APP_ID = "15731769";
    public static final String BAIDU_API_KEY = "jcgVOXXTIKcgDyFm5lRXWDEB";
    public static final String BAIDU_SECRET_KEY = "2aZhvM1OTdWvlNpZt1QNQGQNaifiuUs5";
    
    public final static String YYYYMMDD = "yyyy-MM-dd";
	public final static String YMDHM = "yyyy-MM-dd HH:mm";
	public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String YMDHMSTZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static SimpleDateFormat DATE_YMDHMS = new SimpleDateFormat(YMDHMS);
	public final static SimpleDateFormat DATE_YMDHM = new SimpleDateFormat(YMDHM);
	public final static SimpleDateFormat DATE_YMD = new SimpleDateFormat(YYYYMMDD);
	public final static String BASE_INTERFACE_URL = "http://10.10.130.41:7001/";
}
