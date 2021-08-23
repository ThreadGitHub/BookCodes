package thread.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import thread.consumer.api.UserService;
import thread.consumer.domain.User;

/**
 * extends UserService 通过集成接口的方式来扩展自己
 */
@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserFeignClient extends UserService {
    //Feign默认的Client的请求写法
    //根据用户id获取用户名称
//    @RequestLine("GET /user/{id}")
//    public String getUserNameById(@Param("id") int id);


    //SpringMVC契约模式
    @GetMapping("/get/user")
    public String getUserNameByIdGET(@RequestParam int id, @RequestParam String name);

    @PostMapping("/post/user")
    public String getUserNameByIdPOST(@RequestBody User user);
}
