package thread.ribbon;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultRibbonConfig {
    /**
     * 选择一个并发最小的进行分配
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new BestAvailableRule();
//        return new PingUrl();
    }

    /**
     * IPing机制
     * 用来判断服务节点存活的一种机制，pringUrl 是发一次 http请求来判断是否存活
     * @return
     */
    @Bean
    public IPing ribbonPing(){
        return new DummyPing();
    }

    /**
     * 剩下的这两种配置先不做了解了 书中也没有细说 应该也是一种配置的扩展
     * @return
     */
    /*
    @Bean
    public ServerListSubsetFilter serverListSubsetFilter(){
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        return filter;
    }

    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config){
        return new BazServiceList(config);
    }

    public static class BazServiceList extends ConfigurationBasedServerList{
        public BazServiceList(IClientConfig config){
            super.initWithNiwsConfig(config);
        }
    }
     */
}
