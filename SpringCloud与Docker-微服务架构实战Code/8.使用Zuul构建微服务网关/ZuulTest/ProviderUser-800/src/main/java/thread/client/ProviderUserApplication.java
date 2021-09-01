package thread.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 用户服务 提供用户信息
 * @author Thread丶
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties
public class ProviderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }
}
