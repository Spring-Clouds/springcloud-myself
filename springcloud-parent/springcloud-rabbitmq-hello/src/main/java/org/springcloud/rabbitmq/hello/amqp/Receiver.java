/**
 * Project Name:springcloud-rabbitmq-hello
 * File Name:Receiver.java
 * Package Name:org.springcloud.rabbitmq.hello.amqp
 * Date:2019年2月1日下午5:13:27
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.rabbitmq.hello.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:Receiver <br/>
 * Function: 消息消费者. <br/>
 * Date:     2019年2月1日 下午5:13:27 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RabbitListener(queues="hello")	// 定义该类对 hello 队列的监听
@Component
public class Receiver {
	
	@RabbitHandler // 指定对消息的处理方法
	public void proces(String hello) {
		System.out.println("Receiver :" + hello);
	}
	
	

}

