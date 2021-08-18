## 5.使用Ribbon实现客户端侧负载均衡

### 5.2为服务消费者整合Ribbon - (使用默认的Ribbon负载均衡)

#### 1.导入Ribbon启动器(如果导入了Eureka Client 那么默认包含Ribbon不用导入)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

#### 2.配置RestTemplate 增加 @LoadBalanced

```java
@Bean
@LoadBalanced  //在RestTemplate bean这里开启 Ribbon 负载均衡
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

#### 3.RestTemplate使用虚拟主机名访问微服务

```java
@Autowired
private DiscoveryClient discoveryClient;

@Autowired
private RestTemplate restTemplate;

/**
 * rebbon集成了以后 可以直接通过 虚拟主机名 RestTemplate 进行请求 ribbon会根据默认的设置进行负载均衡 请求每个实例
 * @return
 */
@RequestMapping("/hello")
public List<String> hello(){
    List userNames = restTemplate.getForObject("http://thread-produce/getUserNames", List.class);
    return userNames;
}
```

