/**
 * Project Name:springcloud-rabbitmq-hello
 * File Name:RabbitmqApplication.java
 * Package Name:org.springcloud.rabbitmq.hello
 * Date:2019年2月1日下午5:04:50
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.rabbitmq.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:RabbitmqApplication <br/>
 * Function: 应用主类. <br/>
 * Date:     2019年2月1日 下午5:04:50 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@SpringBootApplication
public class RabbitmqhelloApplication {
	
	public static void main(String[] args) {
//		new SpringApplicationBuilder(RabbitmqhelloApplication.class).web(true).run(args);
		SpringApplication.run(RabbitmqhelloApplication.class, args);
	}

}

