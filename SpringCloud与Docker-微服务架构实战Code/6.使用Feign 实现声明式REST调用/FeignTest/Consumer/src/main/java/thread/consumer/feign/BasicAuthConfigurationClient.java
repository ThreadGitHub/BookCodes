package thread.consumer.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basicAuth-produce",configuration = BasicAuthConfiguration.class)
public interface BasicAuthConfigurationClient {

    @RequestLine("GET /getInfo")
    public String getInfo();

}
