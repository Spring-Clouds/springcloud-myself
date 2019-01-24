package org.springcloud.feign.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * ClassName: FeignConsumerApplication <br/>
 * date: 2018年12月26日 上午9:54:06 <br/>
 *
 * @version 
 * @since JDK 1.8
 * @author kaiyun
 */
@EnableFeignClients		// 开启 Spring Cloud Feign 的支持功能
@EnableDiscoveryClient	// 让应用注册为 Eureka 客户端应用，以获得服务发现的能力。
@SpringBootApplication
public class FeignConsumerApplication{
	
    public static void main( String[] args )
    {
    	SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
