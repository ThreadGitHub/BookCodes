package thread.consumer.feign;

import feign.Contract;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserConfigurationClient {

    /**
     * Feign默认支持的 契约配置形式
     * @return
     */
    @RequestLine("GET /getUserNames")
    public List<String> getUserNames();

}
