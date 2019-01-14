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

/**
 * ClassName:UserCommand <br/>
 * Function: 为请求合并的实现准备一个批量请求命令的实现（详细见 5.3.3） . <br/>
 * Date:     2019年1月11日 下午3:00:45 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {
	
	private UserService userService;
	private List<Long> userIds;
	
	
	public UserBatchCommand(UserService userService, List<Long> userIds) {
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
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(null);
		user.setUserName("调用出异常啦，启用熔断器");
		userList.add(user);
        return userList;
    }

	@Override
	protected List<User> run() throws Exception {
		return userService.findAll(userIds);
	}
}

