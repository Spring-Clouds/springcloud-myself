/**
 * Project Name:springcloud-config-server
 * File Name:ConfigServerApplication.java
 * Package Name:org.springcloud.config.server
 * Date:2019年1月31日下午4:07:35
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.config.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * ClassName:ConfigServerApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月31日 下午4:07:35 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableConfigServer	//开启 Spring Cloud Config 的服务端功能
@SpringBootApplication
public class ConfigServerApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServerApplication.class).web(true).run(args);
	}

}

