/**
 * Project Name:springcloud-hystrix-dashboard
 * File Name:HystrixDashboardApplication.java
 * Package Name:org.springcloud.hystrix.dashboard
 * Date:2019年1月16日下午5:20:10
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.turbine.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * ClassName:TurbineAMQPApplication <br/>
 * Function: Turbine 集群监控 - 与消息代理结合. <br/>
 * Date:     2019年1月16日 下午5:20:10 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableTurbineStream	// 启用 Turbine Stream 的配置
@EnableDiscoveryClient
@SpringBootApplication
public class TurbineAMQPApplication {
	public static void main(String[] args) {
		SpringApplication.run(TurbineAMQPApplication.class, args);
	}
}

