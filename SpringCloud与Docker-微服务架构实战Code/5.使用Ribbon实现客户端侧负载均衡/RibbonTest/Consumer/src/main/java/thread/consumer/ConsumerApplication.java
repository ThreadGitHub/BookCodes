package thread.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
//配置默认的Ribbon 配置策略
//@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
//让配置的Ribbon 自定义配置生效
//@RibbonClient(name="thread-produce", configuration = RibbonConfiguration.class)
public class ConsumerApplication {
    @Bean
    @LoadBalanced  //在RestTemplate bean这里开启 Ribbon 负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
