/**
 * Project Name:springcloud-hystrix-dashboard
 * File Name:HystrixDashboardApplication.java
 * Package Name:org.springcloud.hystrix.dashboard
 * Date:2019年1月16日下午5:20:10
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * ClassName:TurbineApplication <br/>
 * Function: Turbine 集群监控. <br/>
 * Date:     2019年1月16日 下午5:20:10 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@EnableTurbine	// 开启 Turbine（集群监控）
@EnableHystrixDashboard
@SpringBootApplication
public class TurbineApplication {
	public static void main(String[] args) {
		SpringApplication.run(TurbineApplication.class, args);
	}
}

