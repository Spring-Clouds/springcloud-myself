/**
 * Project Name:springcloud-feign-consumer
 * File Name:FullLogConfiguration.java
 * Package Name:org.springcloud.feign.consumer.configbean
 * Date:2019年1月29日下午3:50:11
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.feign.consumer.configbean;
/**
 * ClassName:FullLogConfiguration <br/>
 * Function: Feign 日志配置：通过实现配置类来实现，然后在具体的 Feign 客户端来指定配置类以实现是否要调整不同的日志级别. <br/>
 * Date:     2019年1月29日 下午3:50:11 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

@Configuration
public class FullLogConfiguration {
	
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}

