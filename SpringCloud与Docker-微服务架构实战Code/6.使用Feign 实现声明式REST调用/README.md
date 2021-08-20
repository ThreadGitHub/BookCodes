#  6.使用Feign实现声明式REST调用

![FeignRest调用](README.assets/FeignRest%E8%B0%83%E7%94%A8.png)

## 使用RestTemplate服务之间的REST调用方式缺点

> 拼接url的方式对于简单的还可以，但是现实中对于多参数url复杂那么就会变得难以维护和复杂化，例如下面书中给到写法：

```java
//url例子
String url = "http://localhost:8010/search?name=张三&username=account1&age=20";

//调用实现
public User[] findById(String name, String userName, Integer age){
    Map<String,Object> paramMap = Maps.newHashMap();
    paramMap.put("name",name);
    paramMap.put("userName",userName);
    paramMap.put("age",age);
    return this.restTemplate.getForObject("http://localhost:8010/search?name={name}&username={username}&age={age}", User[].class, paramMap);
}
```

## 6.1 Feign简介

> Feign 是 Netflix开发的声明式、模板化的HTTP客户端.
>
> Feign 使用简单，通过创建一个接口来显示声明调用的REST资源，通过一些注解就可以来实现调用
>
> Feign 也依赖于Ribbon实现了 负载均衡

## 6.2 为服务消费者整合 Feign

### 添加 Feign 的依赖

```xml
<!--导入 Feign 依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

### 开启FeignCleint

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //开启Feign全部的Client
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}

```

### 创建服务的FeignClient

```java
@FeignClient("thread-produce")
public interface UserFeignClient {
    /**
     * 调用 服务生产者 提供的 /getUserNames 资源
     * @return
     */
    @RequestMapping(value = "/getUserNames")
    public List<String> getUserNames();
}
```

### 测试调用生产者的服务

> 调用消费者服务的测试接口发现 Feigin 自动的实现了Ribbon的负载均衡

```java
@RestController
public class HelloController {
    //直接把Feign的接口注册进来,Feign会自动创建代理对象
    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping("/hello")
    public List<String> hello(){
        List<String> userNames = userFeignClient.getUserNames();
        return userNames;
    }
}
```

