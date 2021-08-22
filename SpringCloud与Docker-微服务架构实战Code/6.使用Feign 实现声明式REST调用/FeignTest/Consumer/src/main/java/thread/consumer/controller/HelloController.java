package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.consumer.feign.UserConfigurationClient;
import java.util.List;

@RestController
public class HelloController {
    /*
    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping("/hello")
    public List<String> hello(){
        List<String> userNames = userFeignClient.getUserNames();
        return userNames;
    }
    */

    @Autowired
    private UserConfigurationClient userConfigurationClient;

    @RequestMapping("/hello2")
    public List<String> hello2(){
        List<String> userNames = userConfigurationClient.getUserNames();
        return userNames;
    }

//    @Autowired
//    private BasicAuthConfigurationClient basicAuthConfigurationClient;
//
//    @RequestMapping("/hello3")
//    public String hello3(){
//        return basicAuthConfigurationClient.getInfo();
//    }

}
