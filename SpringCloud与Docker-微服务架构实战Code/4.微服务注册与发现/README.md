# 4. 微服务注册与发现（Eureka）

> 服务发现架构图

![微服务注册与发现](README.assets/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0.png)

服务和服务之前的调用可以使用 HTTP 和 RPC 两种调用方式，SpringCloud使用的时候 HTTP 方式

服务和服务之间肯定存在一种调用者和被调用者的关系，所以就产生了  服务提供者和服务消费者两个角色

服务统一管理信息的服务，就是服务注册中心，也是服务发现组件

## 服务和服务之间进行调用的缺点

服务和服务之间的调用如果没有一个统一的管理中心那么服务一多，可能自己都理不清楚关系了，

还有就是HTTP调用在每个服务中硬编码链接的形式也不可取，所以就出现了服务注册中心进行统一管理

## 服务注册中心的作用

各个微服务在启动时，将自己的网络地址等信息注册到服务发现组件中，服务发现组件会存储这些信息

服务消费者从服务发现组件中查询服务提供者的网络地址，并使用改地址调用服务提供者的接口

各个微服务与服务发现组件使用一定机制（例如心跳）通信。服务发现组件若长时间无法与某服务实例通信，就会注销该实例

## Eureka简介

Eureka 是 Netflix 开源的服务发现组件，本身是一个基于Rest 的服务。它包含Server 和 Client两部分。Spring Cloud 将它集成在子项目Spirng Cloud Netflix 中，从而实现了微服务的注册与发现。

## 环境依赖版本

> 注意：SpringCloud 和 SpringBoot请到Spring官网查看版本对应关系 避免启动时一些依赖问题的错误

```xml
<!--SpringBoot依赖 2.2.7.RELEASE-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.7.RELEASE</version>
    <type>pom</type>
</dependency>

<!--SpringCloud依赖 Hoxton.SR7-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Hoxton.SR7</version>
    <type>pom</type>
    <scope>runtime</scope>
</dependency>

<!--SpringCloud Eureka Client 2.2.4.RELEASE-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>

<!--SpringCloud Eureka Server 2.2.4.RELEASE-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

## Eureak简单的服务注册

### 编写EurekaServer

### 导入依赖

```xml
<!--SpringCloud Eureka Server 2.2.4.RELEASE-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

#### 配置启用EurekaServer

```java
@SpringBootApplication
@EnableEurekaServer	//启用EurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

#### 增加服务配置

```yaml
server:
  port: 8671

#配置Eureka
eureka:
  client:
    register-with-eureka: false #不注册到Eureka 因为自己就是server
    fetch-registry: false #自己不从Eureka服务获取注册信息 因为自己就是server
    service-url:
      defaultZone: http://localhost:8671/eureka #配置Eureaka的服务地址
```

### 编写EurekaClient

### 导入依赖

```xml
<!--SpringCloud Eureka Client 2.2.4.RELEASE-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

#### 配置启用EurekaClient

```java
@SpringBootApplication
@EnableEurekaClient	//启用EurekaClient
public class EurekaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
```

#### 增加服务端配置

```yaml
server:
  port: 880
  
#配置EurekaClient
eureka:
  client:
    service-url:
      #这里是要注册到的Server的地址也就是EurekaServer写的地址
      defaultZone: http://localhost:8671/eureka	
  instance:
    prefer-ip-address: true

spring:
  application:
    name: thread-eureka-client  #配置微服务应用名称
  #配置是否服务自动注册
  cloud:
    service-registry:
      auto-registration:
        enabled: true
```

>  启动测试 先启动Server再启动Client，访问http://localhost:8671就可以看到注册到Eureka了	
>
> ![chrome_CRQRYQU59Q](README.assets/chrome_CRQRYQU59Q.png)

## EurekaServer页面增加用户认证

### 增加SpringSecurity依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 增加SpringSecurity安全认证 BasicHTTP认证方式

```java
@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭CSRF攻击防御，如不关闭，Eureka Client则会找不到Eureka Server而报异常
        http.csrf().disable();
        //注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录,
        //所以必须是httpBasic,如果是form方式,不能使用url格式登录
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
```

```yaml
//增加SpringSecurity的用户
spring:
  application:
    name: eureka-server
  security:
    user:
      name: admin
      password: 123
```

### Server和Client的URL地址都改成http://${user}:${password}@${host}:${port}/eureka/ 这种方式

#### EurekaServer配置-单service-url配置其他同上

```yaml
#配置Eureka
eureka:
  client:
    service-url:
      defaultZone: http://admin:123@localhost:8671/eureka #配置Eureaka的服务地址
```

#### EurekaClient配置 - 单service-url配置其他同上

```yaml
#配置EurekaClient
eureka:
  client:
    service-url:
      #这里是要注册到的Server的地址也就是EurekaServer写的地址
      defaultZone: http://admin:123@localhost:8671/eureka	
```

## Eureka集群高可用

### Eureka集群原理

> Eureka集群其实是多个eurekaServer相互之间注册，他们会互相共享自己的Client彼此同步信息
>
> Eureka相互注册是默认行为，所以不做集群的时候要把 EurekaServer 的 `register-with-eureka: false`   `fetch-registry: false` 这两项设置为false

### 配置HOST文件 配置两个域名

``` properties
127.0.0.1 eureka-server eureka-server2 eureka-server3
```

### 配置第一个Server 配置Server1 注册到 Server2

```yaml
#Eureka 8671的server
---
server:
  port: 8671
spring:
  profiles: eureka-server
eureka:
  instance:
    hostname: eureka-server
  #配置当前server要连接的server的地址
  client:
#    register-with-eureka: false
#    fetch-registry: false
#    service-url:
#      defaultZome: http://eureka-server:8671/eureka
    #写的是要集群连接到的server
    service-url:
      defaultZone: http://eureka-server2:8672/eureka
```

### 配置第二个Server 配置server 2 注册到 Server1

```yaml
#Eureka 8672的 server
---
server:
  port: 8672
spring:
  profiles: eureka-server2
eureka:
  instance:
    hostname: eureka-server2
  #配置当前server要连接的server的地址
  client:
    #写的是要集群连接到的server
    service-url:
      defaultZone: http://eureka-server:8671/eureka
```

> 这样启动两个服务他们之间就形成了集群

### 服务也可以合并为一个服务 service-url 可以合并到一起  如：

#### 增加SpringBoot打包插件

```xml
<!--SpringBoot打包部署的时候要引入SpringBoot打包插件 这样打出来的包才能 java -jar部署-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>2.5.3</version>
        </plugin>
    </plugins>
</build>
```

```yaml
#-------------统一配置所有的 defaultZone到公共的 profile配置里 然后每个根据不同的hostname 去匹配不同的Eureka服务器
#测试 是打包的形式 启动不通的profile配置来互相链接测试  eureka不能通过localhost来互相连接
#修改hosts文件：127.0.0.1 eureka-server eureka-server2 eureka-server3
#java -jar .\EurekaServers-1.0-SNAPSHOT.jar --spring.profiles.active=eureka-server
#java -jar .\EurekaServers-1.0-SNAPSHOT.jar --spring.profiles.active=eureka-server2
#java -jar .\EurekaServers-1.0-SNAPSHOT.jar --spring.profiles.active=eureka-server3

spring:
  application:
    name: eureka-server
  security:
    user:
      name: admin
      password: 123

eureka:
  client:
    #所有的EurekaServer的连接都合并到一起
    service-url:
      defaultZone: http://admin:123@eureka-server:8671/eureka, http://admin:123@eureka-server2:8672/eureka, http://admin:123@eureka-server3:8673/eureka

#Eureka 服务器1  http://eureka-server:8671/eureka
---
server:
  port: 8671
spring:
  profiles:
    eureka-server
eureka:
  instance:
    hostname: eureka-server

#Eureka 服务器2  http://eureka-server2:8672/eureka
---
server:
  port: 8672
spring:
  profiles:
    eureka-server2
eureka:
  instance:
    hostname: eureka-server2

#Eureka 服务器3 http://eureka-server3:8673/eureka
---
server:
  port: 8673
spring:
  profiles:
    eureka-server3
eureka:
  instance:
    hostname: eureka-server3
```

## Eureka健康检查开关

```yaml
eureka:
    client:
        healthcheck:
            enabled: true
```

