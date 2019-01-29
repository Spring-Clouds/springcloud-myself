package org.springcloud.feign.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Logger;

/**
 * ClassName: FeignConsumerApplication <br/>
 * date: 2018年12月26日 上午9:54:06 <br/>
 *
 * @version 
 * @since JDK 1.8
 * @author kaiyun
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableDiscoveryClient	// 让应用注册为 Eureka 客户端应用，以获得服务发现的能力。
@EnableFeignClients		// 开启 Spring Cloud Feign 的支持功能，扫描声明它们是feign客户端的接口(通过@FeignClient)
public class FeignConsumerApplication{
	
	/**
	 * feignLoggerLevel: Feign 日志配置. <br/>
	 * 	（1）、在配置文件里开启指定 Feign 客户端的 DEBUG 日志. <br/>
	 * 	（2）、由于 Feign 客户端默认的 Logger.Level 对象定义为 NONE 级别，
	 * 		该级别不会记录任何 Feign 调用过程中的信息，所以我们需要调整它的级别. <br/>
	 * 	（3）、也可以通过实现配置类来实现（见FullLogConfiguration.java）. <br/>
	 */
//	@Bean
//	Logger.Level feignLoggerLevel() {
//		return Logger.Level.FULL;
//	}
	
    public static void main( String[] args )
    {
    	SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
