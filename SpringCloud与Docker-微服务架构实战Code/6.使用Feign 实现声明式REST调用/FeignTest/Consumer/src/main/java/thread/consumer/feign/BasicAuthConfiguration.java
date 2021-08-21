package thread.consumer.feign;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

public class BasicAuthConfiguration {

    /**
     * 采用Feign默认的注解契约
     * @return
     */
    @Bean
    public Contract contract(){
       return new Contract.Default();
    }

    /**
     * 配置 BasicAuth 密码认证
     * @return
     */
    @Bean
    public BasicAuthenticationInterceptor basicAuthenticationInterceptor(){
        return new BasicAuthenticationInterceptor("root","root");
    }

}
