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

## 6.3 自定义Feign 配置

### Feign 可以配置的选项

#### 目前书中介绍到的配置

- 配置 Contract Bean的实现类  取值：SpringMvcContract 采用SpringMVC契约注解  Contract.Default 启用默认的Feign提供的契约注解

### 使用Java配置类形式

#### 声明 Feign的配置类

> 配置类的@Configuration注解加和不加  取决于不要被SpringBoot默认@ComponentScan注解扫描到
>
> 如果加了注解被扫描到后，Feign所有的Client都会共享这个设置，就达不到我们为单个client加配置的目的了

```java
/**
 * 这里不能加@configuration注解, 因为这个注解加了以后会被主程序的 @ComponentScan扫描到
 * 扫描到后的话 会被 全部的 FeignClient共享这个设置
 */
//@Configuration
public class FeignConfiguration {
    /**
     * Feign 默认的契约是 Spring MVC的注解  用的是 SpringMvcContract 所以 FeignClient 可以使用mvc注解来去定义
     * Contract.Default() 改成了 Feign默认的契约 所以这里配置了以后就要修改FeignClient的注解
     * @return
     */
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
//        SpringMvcContract
    }
}
```

#### 在 FeignCleint 中使用 Feign 配置类

```java
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
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
```

#### 启用全局默认的配置

```java
//开启全局的Feign配置
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)    
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
```

### 使用SpringBoot配置文件形式

```yaml
feign:
  client:
    config:
      #为单独的服务配置Feign配置 (这里的名字是你的服务的名字)
      thread-produce:
        loggerLevel: full #配置Feign日志 为full
      #配置全局的Feign配置
      default:
        loggerLevel: BASIC
```

### Feign的日志配置

#### Feign的日志级别

- NONE	无记录
- BASIC  只记录请求方法，URL，响应状态代码，执行时间
- HEADERS 记录基本信息，请求和响应标头
- FULL 记录请求和响应的头文件，正文和元数据

#### 开启Debug日志级别

默认的Feign只对Debug级别提供显示，所以修改日志级别为debug

```yaml
logging:
  level:
	#要打印日志的 FeignClient 的包路径
    thread.consumer.feign: debug
```

#### yaml配置Feign的日志显示的内容的级别

```yaml
feign:
  client:
    config:
      #为单独的 服务配置Feign配置
      thread-produce:
        loggerLevel: full #配置Feign日志 为full
      #配置全局的Feign配置
      default:
        loggerLevel: BASIC
```

#### java配置类配置，在feign配置类中加入Logger.level 配置

```java
/**
 * 配置 Feign 的日志级别
 * @return
 */
@Bean
public Logger.Level level(){
    return Logger.Level.FULL;
}
```

#### 让Feign可以再INFO级别下显示

**重写Feign的Logger的实现类方式，默认应该是用的feign.slf4j.Slf4jLogger这个类，比着这个实现一下把DEBUG处理改成INFO**

```java
/**
 * 重写feign.Logger实现 用SpringBoot的自定义配置 大于 原本自动注入的原则 这里重写了实现 优先用这里 
 * 把下面原本判断Debug级别的位置 换成INFO
 */
public class ThreadFeignLogger extends feign.Logger{
    private final org.slf4j.Logger logger;

    public ThreadFeignLogger() {
        this(Logger.class);
    }

    public ThreadFeignLogger(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public ThreadFeignLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    ThreadFeignLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (this.logger.isInfoEnabled()) {
            super.logRequest(configKey, logLevel, request);
        }

    }
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        return this.logger.isInfoEnabled() ? super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime) : response;
    }
    @Override
    protected void log(String configKey, String format, Object... args) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}
```

**在Feign的 全局 或 自定义 服务配置类中声明这个重写的@Bean**

```java
public class FeignConfiguration {
    /**
     *让Feign可以再INFO级别下显示
     */
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }
    
    @Bean
    public Logger logger(){
        return new ThreadFeignLogger();
    }
}
```

