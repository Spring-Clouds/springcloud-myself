/**
 * Project Name:hello-service-api
 * File Name:User.java
 * Package Name:org.hello.service.api.dto
 * Date:2019年1月11日下午3:02:32
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.hello.service.api.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClassName:User <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2019年1月11日 下午3:02:32 <br/>
 * @author   kaiyun
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@ToString(exclude="id")
@NoArgsConstructor
@Data
public class User implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String userName;
	
	private int age;

	public User(String userName, int age) {
		super();
		this.userName = userName;
		this.age = age;
	}

	public User(Long id, String userName, int age) {
		super();
		this.id = id;
		this.userName = userName;
		this.age = age;
	}
	
	

}

