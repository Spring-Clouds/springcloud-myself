/**
 * Project Name:springcloud-rabbitmq-hello
 * File Name:Sender.java
 * Package Name:org.springcloud.rabbitmq.hello.amqp
 * Date:2019年2月1日下午5:10:01
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.rabbitmq.hello.amqp;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:Sender <br/>
 * Function: 消息生产者. <br/>
 * Date:     2019年2月1日 下午5:10:01 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class Sender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send() {
		String context = "hello " + new Date(); 
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}

}

