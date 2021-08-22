package thread.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("basicAuth-produce")
public interface BasicAuthFeignClient {

    @RequestMapping("/loginName")
    public String getLoginName();

}
