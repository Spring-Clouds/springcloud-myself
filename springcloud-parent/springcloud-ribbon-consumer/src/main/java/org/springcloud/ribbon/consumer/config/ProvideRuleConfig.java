package org.springcloud.ribbon.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;

@Component
public class ProvideRuleConfig {
	
	/**
	 * 制定客户端Ribbon负载均衡策略
	 * @return
	 */
	@Bean
    public IRule ribbonRule() {
		 // 随机选择一个server
//        return new RandomRule();
        
        // roundRobin方式轮询选择server
        return new RoundRobinRule();
    }

}
