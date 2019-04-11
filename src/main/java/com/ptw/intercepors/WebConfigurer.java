package com.ptw.intercepors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ptw.utils.SystemParams;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	@Autowired
	private SystemParams params;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		//其中image表示访问的前缀。"file:E:/image/"是文件真实的存储路径
		String path = "file:"+params.getImagePath();
		registry.addResourceHandler("/image/**").addResourceLocations(path);
	}
}
