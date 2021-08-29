# 7. 使用 Hystrix 实现容错

## 7.1.1 雪崩效应

![雪崩效应](README.assets/%E9%9B%AA%E5%B4%A9%E6%95%88%E5%BA%94.png)

## Hystrix 如果实现容错

Hystrix 提供了一个断路器机制，下面是它的一个运行逻辑

正常情况下 服务A 调用 服务B 能成功的情况下 断路器关闭状态

服务A 调用 服务B 调用不成功 没有到 Hystrix 的阀值 断路器关闭状态，如果到了阀值那么 断路器会进入打开状态

断路器打开一段的时间不在请求依赖的服务，等过一段时间后会进入半开状态，断路器可允许其中一次请求，去尝试请求依赖的服务，如果还是请求失败那么，断路器重新进入打开状态，不在请求依赖的服务，循环往复，直到断路器半开状态，其中一次请求能请求通依赖的服务后，断路器会重新进入关闭状态，服务之间正常调用

## 7.2 使用 Hystrix 实现容错

### 7.2.2 使用通用方式整合Hystrix RestTemplate

#### 导入Hystrix的依赖

```xml
<!--Hystrix依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

#### 通过@HystrixCommand注解实现容错

```java
/**
 * 调用用户服务 根据用户id 获取用户名称
 * @param id
 * @return
 */
@RequestMapping("/getUserNameById")
//HystrixCommand容错 设置请求失败后的设置回退方法
@HystrixCommand(fallbackMethod = "getUserNameByIdFallBack" 
                /*, ignoreExceptions = {ArithmeticException.class}*/) 
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
```

通过@HystrixCommand注解在RestTemplate方法级别上加上容错，保证在方法失败返回异常的时候，进行一个容错处理，就是指定的`fallbackMethod`的回退方法的处理，来进行一个错误后的回退处理。`ignoreExceptions`属性可以指定在出现某种异常时不进行容错

### 7.2.3 Hystrix 断路器的状态监控与深入理解

#### 利用SpringBootActuator监控模块查看断路器状态

引入监控依赖

```xml
<!--集成监控模块-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

配置监控的设置

```yaml
#Actuator 配置访问的路径是 根路径/ 然后 暴露全部监控接口
management:
  endpoints:
    web:
      exposure:
        include: "*" #配置暴露全部的监控接口
      base-path: /  #配置访问路径
  endpoint:
    health:
      show-details: always  #配置健康接口显示详细信息
```

#### 查看Hystrix健康状态

> Hystrix 状态：
>
> 默认如果 status = UP  断路器是关闭 一切正常
>
> 如果是 status = CIRCUIT_OPEN 说明短路器是打开状态，表示服务不可用状态

通过地址访问链接的形式查看

![chrome_JYBbQA1qV2](README.assets/chrome_JYBbQA1qV2-16302550503012.png)

通过IDEA的Services的Health功能查看

![image-20210830003635243](README.assets/image-20210830003635243.png)

