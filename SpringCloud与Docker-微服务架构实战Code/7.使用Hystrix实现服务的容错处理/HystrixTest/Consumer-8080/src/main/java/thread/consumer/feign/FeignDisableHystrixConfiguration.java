package thread.consumer.feign;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 禁用单个FeignClient的配置
 * 全局禁用 feign.hystrix.enabled = false
 * @author Thread丶
 */
public class FeignDisableHystrixConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(){
        return new Feign.Builder();
    }
}
