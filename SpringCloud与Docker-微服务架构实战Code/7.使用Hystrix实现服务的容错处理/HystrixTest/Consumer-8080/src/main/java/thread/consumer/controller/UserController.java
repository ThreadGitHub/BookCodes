package thread.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 消费者服务 RestTemplate 消费 用户服务
 * 测试Hystrix
 * @author Thread丶
 */
@RestController
@RequestMapping("/userController")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用用户服务 根据用户id 获取用户名称
     * @param id
     * @return
     */
    @RequestMapping("/getUserNameById")
    @HystrixCommand
    (
        //HystrixCommand容错 设置请求失败后的设置回退方法
        fallbackMethod = "getUserNameByIdFallBack",

        //设置不在容错范围内的异常 出现以下异常不走容错处理
        ignoreExceptions = {ArithmeticException.class},

        //Hystrix 隔离机制分为 THREAD(线程隔离-线程池实现)  SEMAPHORE(信号量隔离-使用调用的线程处理)
        commandProperties = {
            //设置隔离机制 为 信号量隔离
            @HystrixProperty(name="execution.isolation.strategy", /*value = "SEMAPHORE",*/ value = "THREAD")
        }
    )
    public String getUserNameById(int id){
        //通过 ignoreExceptions 设置过滤掉的异常 当触发这类异常时候不进行容错
//        int i = 10 / 0;
        String url = "http://localhost:8081/getUserNameById?id={0}";
        String userName = restTemplate.getForObject(url, String.class, id);
        return userName;
    }

    /**
     * 获取用户名称失败后的回退方法 返回一个默认的用户名
     * fallbackMethod 可以通过增加  Throwable参数来获取报错信息
     * @param id
     * @return
     */
    public String getUserNameByIdFallBack(int id, Throwable throwable){
        throwable.printStackTrace();
        return "default";
    }
}
