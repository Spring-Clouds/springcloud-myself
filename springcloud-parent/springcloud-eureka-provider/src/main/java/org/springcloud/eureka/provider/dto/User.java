/**
 * Project Name:springcloud-ribbon-consumer
 * File Name:User.java
 * Package Name:org.springcloud.ribbon.consumer.dto
 * Date:2019年1月11日下午3:02:32
 * Copyright (c) 2019, kaiyun@qk365.com All Rights Reserved.
 *
*/

package org.springcloud.eureka.provider.dto;

import java.io.Serializable;

import lombok.Data;
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
@ToString(callSuper=true, includeFieldNames=true)
@Data
public class User implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String userName;
}

