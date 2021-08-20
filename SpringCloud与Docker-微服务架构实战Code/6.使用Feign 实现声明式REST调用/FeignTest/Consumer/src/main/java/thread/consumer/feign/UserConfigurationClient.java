package thread.consumer.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import java.util.List;

@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserConfigurationClient {

    @RequestLine("GET /getUserNames")
    public List<String> getUserNames();

}
