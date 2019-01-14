/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:UserCommand.java
 * Package Name:org.springcloud.ribbon.consumer.customhystrixcommand
 * Date:2019年1月11日下午3:00:45
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.customhystrixcommand;

import org.springcloud.ribbon.consumer.dto.User;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;

/**
 * ClassName:UserCommand <br/>
 * Function: 服务容错保护 Spring cloud Hystrix：自定义请求命令. <br/>
 * Date:     2019年1月11日 下午3:00:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class UserCommand extends HystrixCommand<User> {
	
	private RestTemplate restTemplate;
	private Long id;
	
	
	public UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
		super(setter);
		this.restTemplate = restTemplate;
		this.id = id;
	}
	
	/**
	 * TODO 服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）（可选）.
	 * @see com.netflix.hystrix.HystrixCommand#getFallback()
	 */
	@Override
    protected User getFallback() {
		User user = new User();
		user.setId(null);
		user.setUserName("调用出异常啦，启用熔断器");
        return user;
    }

	@Override
	protected User run() throws Exception {
		return restTemplate.getForObject("http://PROVIDER-SERVICE/users/{1}", User.class, id);
	}
}

