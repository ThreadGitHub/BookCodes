package thread.consumer.feign;

import feign.Contract;
import feign.Logger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

/**
 * 这里不能加@configuration注解, 因为这个注解加了以后会被主程序的 @ComponentScan扫描到
 * 扫描到后的话 会被 全部的 FeignClient共享这个设置
 */
//@Configuration
public class FeignConfiguration {
    /**
     * Feign 默认的契约是 Spring MVC的注解  用的是 SpringMvcContract 所以 FeignClient 可以使用mvc注解来去定义
     * Contract.Default() 改成了 Feign默认的契约 所以这里配置了以后就要修改FeignClient的注解
     * @return
     */
    @Bean
    public Contract feignContract(){
//        return new Contract.Default();
      return new SpringMvcContract();
    }

    /**
     * 配置 Feign 的日志级别
     * @return
     */
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }

    /**
     * 配置重写的Logger允许Feign在INFO级别下输出日志
     * @return
     */
    @Bean
    public Logger logger(){
        return new ThreadFeignLogger();
    }
}
