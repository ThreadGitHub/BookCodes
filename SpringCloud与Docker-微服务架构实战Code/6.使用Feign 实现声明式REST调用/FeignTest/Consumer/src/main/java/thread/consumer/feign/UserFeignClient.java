package thread.consumer.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

//@FeignClient("thread-produce")
public interface UserFeignClient {

    /**
     * 调用 服务生产者 提供的 /getUserNames 资源
     * @return
     */
    @RequestLine("GET /getUserNames")
    public List<String> getUserNames();

}
