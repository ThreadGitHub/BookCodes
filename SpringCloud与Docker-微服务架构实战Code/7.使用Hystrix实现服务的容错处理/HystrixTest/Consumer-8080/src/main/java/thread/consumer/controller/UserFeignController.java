package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import thread.consumer.feign.UserFeignClient;

/**
 * 消费者服务 FeignClient 消费 用户服务
 * Feign 对于 Hystrix 容错的使用
 * @author Thread丶
 */
@RestController
@RequestMapping("/userFeignController")
public class UserFeignController {
    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping(value = "/getUserNameById", method = RequestMethod.GET)
    public String getUserNameById(int id){
        String userName = userFeignClient.getUserNameById(id);
        return userName;
    }
}
