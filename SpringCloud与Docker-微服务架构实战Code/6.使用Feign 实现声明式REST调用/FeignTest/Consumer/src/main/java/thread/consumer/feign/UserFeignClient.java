package thread.consumer.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import thread.consumer.api.UserService;

/**
 * extends UserService 通过集成接口的方式来扩展自己
 */
@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserFeignClient extends UserService {

    //Feign默认的Client的请求写法
    //根据用户id获取用户名称
    @RequestLine("GET /user/{id}")
    public String getUserNameById(@Param("id") int id);


    //SpringMVC契约模式
//    @RequestMapping("/user/{id}")
//    public String getUserNameById(@PathVariable("id") int id);
}
