/**
 * Project Name:springcloud-rabbitmq-hello
 * File Name:RabbitConfig.java
 * Package Name:org.springcloud.rabbitmq.hello
 * Date:2019年2月1日下午5:17:00
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.rabbitmq.hello;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:RabbitConfig <br/>
 * Function: RabbitMQ 的配置类，用来设置队列、交换器、路由等高级信息. <br/>
 * Date:     2019年2月1日 下午5:17:00 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Configuration
public class RabbitConfig {
	
	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}

}

