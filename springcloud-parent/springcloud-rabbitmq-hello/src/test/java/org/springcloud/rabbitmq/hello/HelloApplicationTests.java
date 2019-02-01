/**
 * Project Name:springcloud-rabbitmq-hello
 * File Name:HelloApplicationTests.java
 * Package Name:org.springcloud.rabbitmq.hello
 * Date:2019年2月1日下午5:22:15
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.rabbitmq.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springcloud.rabbitmq.hello.amqp.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:HelloApplicationTests <br/>
 * Function: 单元测试类. <br/>
 * Date:     2019年2月1日 下午5:22:15 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=RabbitmqhelloApplication.class)
public class HelloApplicationTests {
	
	@Autowired
	private Sender sender;
	
	@Test
	public void hello() throws Exception {
		sender.send();
	}

}

