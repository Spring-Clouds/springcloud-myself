/**
 * Project Name:api-gateway-dynamic-filter
 * File Name:FilterConfiguration.java
 * Package Name:org.api.gateway.dynamic.filter.config
 * Date:2019年1月31日下午3:21:56
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.api.gateway.dynamic.filter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * ClassName:FilterConfiguration <br/>
 * Function: 加载自定义属性的配置类. <br/>
 * Date:     2019年1月31日 下午3:21:56 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
@ConfigurationProperties("zuul.filter")
public class FilterConfiguration {
	
	/** 用来指定动态加载的过滤器存储路径 */
	private String root;
	
	/** 用来配置动态加载的间隔时间，以秒为单位 */
	private Integer interval;
	

}

