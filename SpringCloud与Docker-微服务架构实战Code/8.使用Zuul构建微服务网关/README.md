# 8. 使用 Zuul 构建微服务网关

## 8.2 Zuul简介



## 8.3 编写 Zuul 微服务网关

导入网关依赖，网关需要连接到注册中心

```xml
<!--Zuul-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
<!--EurekaClient-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

> 开启网关，只需要在服务启动类上加入 `@EnableZuulProxy //开启Zuul网关`  就可以
>
> 访问默认的网关就可以 通过 `http://localhost:600/[服务名]/[服务资源地址]/[服务资源地址]`访问Eurkea中的微服务了
>
> 如果服务是多实例的情况下那么Zuul也实现了多服务的负载均衡

