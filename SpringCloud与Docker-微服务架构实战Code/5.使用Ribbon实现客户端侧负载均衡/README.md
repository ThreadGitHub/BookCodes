# 5. 使用Ribbon实现客户端侧负载均衡

## 环境版本 - SpringCloud Hoxton.** 和 SpringBoot 2.** 兼容版本

```xml
SpringBoot依赖 2.2.7.RELEASE
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.7.RELEASE</version>
    <type>pom</type>
</dependency>

SpringCloud依赖 Hoxton.SR7
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Hoxton.SR7</version>
    <type>pom</type>
    <scope>runtime</scope>
</dependency>

SpringCloud Eureka Client 2.2.4.RELEASE
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>

SpringCloud Eureka Server 2.2.4.RELEASE
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

## Ribbon负载均衡策略(目前书上没介绍，以后用到了再补充)

| 策略名称          | 策略说明                   |
| ----------------- | -------------------------- |
| RandomRule        | 随机的进行分配             |
| BestAvailableRule | 选择一个并发最小的进行分配 |

## 5.2 为服务消费者整合Ribbon - (使用默认的Ribbon负载均衡)

### 1. 导入Ribbon启动器(如果导入了Eureka Client 那么默认包含Ribbon不用导入)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

### 2. 配置RestTemplate 增加 @LoadBalanced

```java
@Bean
@LoadBalanced  //在RestTemplate bean这里开启 Ribbon 负载均衡
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

### 3. RestTemplate使用虚拟主机名访问微服务	

```java
@Autowired
private DiscoveryClient discoveryClient;

@Autowired
private RestTemplate restTemplate;

/**
 * rebbon集成了以后 可以直接通过 虚拟主机名 RestTemplate 进行请求 Ribbon会根据默认的设置进行负载均衡 请求每个实例
 * @return
 */
@RequestMapping("/hello")
public List<String> hello(){
    List userNames = restTemplate.getForObject("http://thread-produce/getUserNames", List.class);
    return userNames;
}
```

### 4. 通过 LoadBalancerClient.choose()来获取负载均衡后用的是哪个实例

```java
@Autowired
private LoadBalancerClient loadBalancerClient;

/**
  * 获取分配的 生产者实例
  * @return
  */
@RequestMapping("/getProduce")
public String getProduce(){
    ServiceInstance choose = this.loadBalancerClient.choose("thread-produce");
    logger.info("{}",choose.getInstanceId());
    String str = "thread-produce \t" + choose.getUri();
    return str;
}
```

## 5.3 Ribbon配置自定义

### 配置分类：

- **配置负载均衡分配方式：** 		配置 IRule 接口 的不同实现类
- **配置检测服务节点存活方式：** 配置 IPing 接口的不同实现类

其他的.... 用到了的话自行百度吧。

### JAVA配置类形式

#### 配置单个 RibbonClient 负载均衡策略 

> **注意：这个配置类不能放到应用@ComponentScan扫描的包下，不然策略会被Ribbon 的所有Client共享**

##### Ribbon 配置类，通过配置不同接口的实现类通过改变 Ribbbon 的配置规则

```java
/**
 * 配置类 不能放在@ComponentScan所扫描的包中，不然会被Ribbon所有Client共享
 * 因为@SpringBootAppllication 里引用了@ComponentScan 所以单独建了一个包
 */
@Configuration
public class RibbonConfiguration {
    /**
     * 启动 随机的负载均衡策略
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
```

##### @RibbonClient 启用 自定义配置类

```java
//配置默认的Ribbon 配置策略
//name = 服务名称   configuration = Ribbon配置类
@RibbonClient(name="thread-produce", configuration = RibbonConfiguration.class)
```

#### 配置全局 负载均衡策略

##### Ribbon 配置类，通过配置不同接口的实现类通过改变 Ribbbon 的配置规则

```java
@Configuration
public class DefaultRibbonConfig {
    /**
     * 选择一个并发最小的进行分配
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new BestAvailableRule();
//        return new PingUrl();
    }

    /**
     * IPing机制
     * 用来判断服务节点存活的一种机制，pringUrl 是发一次 http请求来判断是否存活
     * @return
     */
    @Bean
    public IPing ribbonPing(){
        return new DummyPing();
    }

    /**
     * 剩下的这两种配置先不做了解了 书中也没有细说 应该也是一种配置的扩展
     * @return
     */
    /*
    @Bean
    public ServerListSubsetFilter serverListSubsetFilter(){
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        return filter;
    }

    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config){
        return new BazServiceList(config);
    }

    public static class BazServiceList extends ConfigurationBasedServerList{
        public BazServiceList(IClientConfig config){
            super.initWithNiwsConfig(config);
        }
    }
     */
}
```

##### 启用默认全局配置

```java
//配置默认的Ribbon 配置策略
//默认的 Ribbon配置类
@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
```

### SpirngBoot配置文件形式配置

#### Ribbon提供的配置属性

| 属性名                            | 属性值                             |
| --------------------------------- | ---------------------------------- |
| **NFLoadBalancerClassName**       | 配置：**ILoadBalancer的实现类**    |
| **NFLoadBalancerRuleClassName**   | 配置：**IRule的实现类**            |
| **NFLoadBalancerPingClassName**   | 配置：**IPing的实现类**            |
| **NIWSServerListClassName**       | 配置：**ServerList的实现类**       |
| **NIWSServerListFilterClassName** | 配置：**ServerListFilter的实现类** |

#### 配置文件格式：

**单个服务的配置 :  服务名.ribbon.属性 = 属性值**

**全局服务的配置： ribbon.属性 = 属性值**

```java
#配置 Ribbon 访问策略
# -- 配置全局默认的策略
ribbon:
  #配置 负载均衡 分配策略
  NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
# -- 单个服务配置策略
thread-produce:
  ribbon:
    #配置 负载均衡 分配策略
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.BestAvailableRule
```

