package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thread.consumer.domain.User;
import thread.consumer.feign.UserFeignClient;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping("/hello")
    public List<User> hello(){
        List<User> users = userFeignClient.getUserNames();
        return users;
    }

    @GetMapping("/get/user")
    public String getUserNameById(int id, String name){
        return userFeignClient.getUserNameByIdGET(id, name);
    }

    @PostMapping("/post/user")
    public String getUserNameById(@RequestBody User user){
        return userFeignClient.getUserNameByIdPOST(user);
    }
}
