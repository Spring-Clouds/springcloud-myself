/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:UserCommand.java
 * Package Name:org.springcloud.ribbon.consumer.customhystrixcommand
 * Date:2019年1月11日下午3:00:45
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.ribbon.consumer.customhystrixcommand;

import java.util.ArrayList;
import java.util.List;

import org.springcloud.ribbon.consumer.dto.User;
import org.springcloud.ribbon.consumer.service.UserService;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:UserCommand <br/>
 * Function: 【服务容错保护Hystrix - 自定义请求命令】为请求合并的实现准备一个批量请求命令的实现（详细见 5.3.3） . <br/>
 * Date:     2019年1月11日 下午3:00:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
public class UserBatchCommand extends HystrixCommand<List<User>> {
	
	private UserService userService;
	private List<Long> userIds;
	
	
	public UserBatchCommand(UserService userService, List<Long> userIds) {
		// 通过继承方式实现的 Hystrix 命令使用类名作为默认的命令名称，我们也可以在构造函数中通过 Setter 静态类来设置
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
		this.userIds = userIds;
		this.userService = userService;
	}
	
	/**
	 * TODO 服务降级的方法（是Hystrix命令执行失败时使用的后备方法，用来实现服务的降级处理逻辑）（可选）.
	 * @see com.netflix.hystrix.HystrixCommand#getFallback()
	 */
	@Override
    protected List<User> getFallback() {
		getExecutionException().getStackTrace();
		
		List<User> userList = new ArrayList<>();
		for(Long userId : userIds) {
			User user = new User();
			user.setId(userId);
			user.setUserName("【合并请求】调用出异常啦，启用熔断器");
			userList.add(user);
		}
        return userList;
    }

	@Override
	protected List<User> run() throws Exception {
		log.info("发送请求。。。参数为：userIds={}, ThreadName={}", userIds.toString(), Thread.currentThread().getName());
		return userService.findAll(userIds);
	}
}

