package thread.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类 不能放在@ComponentScan所扫描的包中，不然会被Ribbon所有Client共享
 * 因为@SpringBootAppllication 里引用了@ComponentScan 所以单独建了一个包
 */
@Configuration
public class RibbonConfiguration {
    /**
     * 启动 随机的负载均衡策略
     * @return
     */
    @Bean
        public IRule ribbonRule(){
        return new RandomRule();
    }
}
