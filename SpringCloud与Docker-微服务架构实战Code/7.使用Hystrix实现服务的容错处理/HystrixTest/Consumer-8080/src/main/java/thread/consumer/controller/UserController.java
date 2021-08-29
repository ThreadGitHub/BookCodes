package thread.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 消费者服务 RestTemplate 消费 用户服务
 * @author Thread丶
 */
@RestController
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用用户服务 根据用户id 获取用户名称
     * @param id
     * @return
     */
    @RequestMapping("/getUserNameById")
    @HystrixCommand(fallbackMethod = "getUserNameByIdFallBack" /*, ignoreExceptions = {ArithmeticException.class}*/) //HystrixCommand容错 设置请求失败后的设置回退方法
    public String getUserNameById(int id){
        //通过 ignoreExceptions 设置过滤掉的异常 当触发这类异常时候不进行容错
        int i = 10 / 0;
        String url = "http://localhost:8081/getUserNameById?id={0}";
        String userName = restTemplate.getForObject(url, String.class, id);
        return userName;
    }

    /**
     * 获取用户名称失败后的回退方法 返回一个默认的用户名
     * @param id
     * @return
     */
    public String getUserNameByIdFallBack(int id, Throwable throwable){
        throwable.printStackTrace();
        return "错误信息：" + throwable.toString() + "<br/>我是回退方法的用户名Default";
    }
}
