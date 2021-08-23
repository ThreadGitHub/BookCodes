package thread.consumer.api;

import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestMapping;
import thread.consumer.domain.User;

import java.util.List;

/**
 * Feign Client 支持集成接口的形式来扩展FeignClient的接口
 * 但是继承过来的接口 他的契约格式要按照FeignClient的契约格式来写
 */
public interface UserService {

    //SpringMVC契约格式  调用 服务生产者 提供的 /getUserNames 资源
    @RequestMapping("/getUserNames")
    public List<User> getUserNames();


    //Feign默认契约格式  调用 服务生产者 提供的 /getUserNames 资源
//    @RequestLine("GET /getUserNames")
//    public List<String> getUserNames();
}
