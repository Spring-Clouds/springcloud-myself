/**
 * Project Name:springcloud-hystrix-dashboard
 * File Name:HystrixDashboardApplication.java
 * Package Name:org.springcloud.hystrix.dashboard
 * Date:2019年1月16日下午5:20:10
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * ClassName:HystrixDashboardApplication <br/>
 * Function: Hystrix 仪表盘. <br/>
    	1、Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题。<br/>
    	2、Dashboard服务是个独立的结点，不需要配置eureka信息，只需要依赖以下jar包。<br/>
 * Date:     2019年1月16日 下午5:20:10 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@SpringBootApplication
@EnableHystrixDashboard	// 启用 Hystrix Dashboard 功能
public class HystrixDashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardApplication.class, args);
	}
}

