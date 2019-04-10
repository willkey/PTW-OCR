package com.ptw.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**获取application.yml中的常量*/
@Component
@ConfigurationProperties(prefix = "system-params")
@Data
public class SystemParams {
	@Value("${system-params.imagePath}")
	private String ImagePath;
}
