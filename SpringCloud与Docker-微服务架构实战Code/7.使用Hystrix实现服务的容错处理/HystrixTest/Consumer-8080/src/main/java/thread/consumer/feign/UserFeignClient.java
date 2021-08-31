package thread.consumer.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务的FeignClient
 * @author Thread丶
 */
@FeignClient(name = "provider-user", /*configuration = FeignDisableHystrixConfiguration.class,*/
            /*fallback = UserFeignClientImpl.class,*/ fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeignClient {
    /**
     * 根据用户id 获取用户名称
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserNameById", method = RequestMethod.GET)
    public String getUserNameById(@RequestParam int id);
}

/**
 * 第一种方式 实现FeignClient接口 实现Hystrix容错 但是获取不到异常信息
 */
@Component
class UserFeignClientImpl implements UserFeignClient{
    @Override
    public String getUserNameById(int id) {
        return "default";
    }
}

/**
 * 第二种方式 实现 FallbackFactory 接口 提供Throwable获取错误信息
 */
@Component
class UserFeignFallbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new UserFeignClient() {
            @Override
            public String getUserNameById(int id) {
                return "default";
            }
        };
    }
}
