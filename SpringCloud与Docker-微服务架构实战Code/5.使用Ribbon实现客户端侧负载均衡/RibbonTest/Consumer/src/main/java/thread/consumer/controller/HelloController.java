package thread.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * rebbon集成了以后 可以直接通过 RestTemplate传递域名方式  进行请求 ribbon会根据默认的设置进行负载均衡 请求每个实例
     * @return
     */
    @RequestMapping("/hello")
    public List<String> hello(){
        List userNames = restTemplate.getForObject("http://thread-produce/getUserNames", List.class);
        return userNames;
    }

    /**
     * 获取分配的 生产者实例
     * @return
     */
    @RequestMapping("/getProduce")
    public String getProduce(){
        ServiceInstance choose = this.loadBalancerClient.choose("thread-produce");
        logger.info("{}",choose.getInstanceId());
        String str = "thread-produce \t" + choose.getUri();
        return str;
    }

}
