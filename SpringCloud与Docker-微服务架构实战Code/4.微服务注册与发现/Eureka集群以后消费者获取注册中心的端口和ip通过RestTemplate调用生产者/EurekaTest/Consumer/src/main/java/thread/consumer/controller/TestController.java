package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUserNames")
    public List<String> getUserName(){
        List<ServiceInstance> eurekaProduce = discoveryClient.getInstances("eureka-produce");
        ServiceInstance serviceInstance = eurekaProduce.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        System.out.println("produce Host : " + host + "\t" + "Port : " + port);
        String url = serviceInstance.getUri() + "/getUserNames";
        List userNames = restTemplate.getForObject(url, List.class);

        List<ServiceInstance> consumer = discoveryClient.getInstances("eureka-consumer");
        ServiceInstance consumerInstance = consumer.get(0);
        System.out.println("consumer Host : " + consumerInstance.getHost() + "\t" + "Port : " + consumerInstance.getPort());

        return userNames;
    }

}
