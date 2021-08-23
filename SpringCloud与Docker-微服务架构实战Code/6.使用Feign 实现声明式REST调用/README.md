#  6.使用Feign实现声明式REST调用

![FeignRest调用](README.assets/FeignRest%E8%B0%83%E7%94%A8.png)

## 环境版本 - SpringCloud Hoxton.** 和 SpringBoot 2.** 兼容版本

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

<!--Feign 2.2.4.RELEASE-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
	<version>2.2.4.RELEASE</version>
</dependency>

<!--Feign对于上传文件的依赖-->
<dependency>
    <groupId>io.github.openfeign.form</groupId>
    <artifactId>feign-form-spring</artifactId>
    <version>3.8.0</version>
</dependency>
<dependency>
    <groupId>io.github.openfeign.form</groupId>
    <artifactId>feign-form</artifactId>
    <version>3.8.0</version>
</dependency>
```

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

```yaml
feign:
    client:
        feignName:
            #相当于Request.Options
            connectTimeout: 5000
            #相当于Request.Options
            readTimeout: 5000
            #配置Feign的日志级别，相当于代码配置方式中的Logger
            loggerLevel: full
            #Feign的错误解码器,相当于代码配置中的ErrorDecoder
            errorDecoder: com.example.SimpleErrorDecoder
            #配置重试,相当于代码配置方式中的Retryer
            retryer: com.example.SimpleRetryer
            #配置拦截器,相当于代码配置方式中的RequestInterceptor
            requestInterceptors:
                - com.example.FooRequestInterceptor
                - com.example.BarRequestInterceptor
            decode404: false
    #feign配置文件的方式比java配置优先级要高,如果想java配置优先级开这里可以设置为false
    default-to-properties: false 
```

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

## 6.4 手动创建FeignClient (Feign.builder)

> 消费者通过BasicHttp验证方式访问生产者服务，根据SprigSecurity提供不同的用户和角色来创建不同的FeignClient
>
> 不同的FeignClient 携带着不同的 用户信息 到了 生产者那里 根据携带信息的权限来进行处理

### 为生产者服务提供BasicHttp验证方式

提供两个用户：[xiaoming  123  角色role-user] 和  [zhangsan 123  角色role-admin]  两个用户 分别对应两个角色

```java
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
    //开启HttpBasic认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    //声明无密码加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private CustomUserDetails customUserDetails;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetails).passwordEncoder(this.passwordEncoder());
    }

    //配置授权和实体的服务
    @Component
    class CustomUserDetails implements UserDetailsService{
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if(username.equals("xiaoming")){
                return new SecurityUser("xiaoming","123", "role-user");
            }else if(username.equals("zhangsan")){
                return new SecurityUser("zhangsan", "123", "role-admin");
            }
            return null;
        }
    }

    //配置用户和授权实体
    class SecurityUser implements UserDetails{
        private String password;
        private String username;
        private String role;

        public SecurityUser(String username, String password, String role){
            this.username = username;
            this.password = password;
            this.role = role;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.role);
            collection.add(simpleGrantedAuthority);
            return collection;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
```

### 生产者服务获取现在登录用户的信息，根据用户信息的不同做不能的处理

```java
//获取登录用户
@RequestMapping("/loginName")
public String getLoginName(){
    String username = "";	//用户名
    String password = "";	//密码
    String role = "";		//角色
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(principal instanceof UserDetails){
        UserDetails userDetails = (UserDetails)principal;
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            username = userDetails.getUsername();
            password = userDetails.getPassword();
            role = grantedAuthority.getAuthority();
            logger.info("用户名：{}\t密码：{}\t角色：{}", username, password, role);
        }
    }
    return "<h1>当前用户</h1><br/>用户名："+ username +"\t密码："+ password +"\t角色：" + role;
}
```

### 消费者服务创建公共的FeignClient

```java
@FeignClient("basicAuth-produce")
public interface BasicAuthFeignClient {
    @RequestMapping("/loginName")
    public String getLoginName();
}
```

### 消费者服务通过Feign.builder创建不同权限Client实例

**`new BasicAuthRequestInterceptor("xiaoming","123")`   通过BasicHttp方式验证用户**

**`target(BasicAuthFeignClient.class,"http://basicAuth-produce:8083")`  要构建的FeignClient实例和生产者服务的url**

```java
@Import(FeignClientsConfiguration.class)
@RestController
public class BasicAuthFeignController {
    //用户FeignClient
    private BasicAuthFeignClient userFeignClient;
    //管理员Feignclient
    private BasicAuthFeignClient adminFeignClient;

    /**
     * 初始化Controller时创建 根据用户和角色的不同 创建不同形态的 Client
     * 利用BasicHttp 进行认证的方式 传输不同的用户名和密码
     * @param decoder
     * @param encoder
     * @param client
     * @param contract
     */
    public BasicAuthFeignController(Decoder decoder, Encoder encoder, Client client, Contract contract){
        /**
         * 这里的构造方法的参数 是通过 @Import(FeignClientsConfiguration.class)导入Bean的方式注入进来的
         */
        this.userFeignClient= Feign.builder().client(client).
            					encoder(encoder).decoder(decoder).contract(contract).
      			requestInterceptor(new BasicAuthRequestInterceptor("xiaoming","123")).
                target(BasicAuthFeignClient.class,"http://basicAuth-produce:8083");

        this.adminFeignClient = Feign.builder().client(client).encoder(encoder).
            					decoder(decoder).contract(contract).
                requestInterceptor(new BasicAuthRequestInterceptor("zhangsan","123")).
                target(BasicAuthFeignClient.class,"http://basicAuth-produce:8083");
    }
    //管理员调用管理员的Client
    @RequestMapping("/admin")
    public String getAdmin(){
        String loginName = adminFeignClient.getLoginName();
        return loginName;
    }
    //普通用户调用普通用户的Client
    @RequestMapping("/user")
    public String getUser(){
        String loginName = userFeignClient.getLoginName();
        return loginName;
    }
}
```

## Feign.builder() 和 BasicAuthRequestInterceptor 的实现的操作过程

```java
Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract).
requestInterceptor(new BasicAuthRequestInterceptor("xiaoming","123"))
```

**`client(client).encoder(encoder).decoder(decoder).contract(contract)` 这一部分正常为FeignClient设置默认的设置信息通过`@Import(FeignClientsConfiguration.class)` 导入来的设置进行的配置**

`requestInterceptor(new BasicAuthRequestInterceptor("xiaoming","123"))` 设置BasicHttp认证的用户名和密码也就是生产者服务SpringSecurity配置的用户信息

#### Feign的BasicAuthRequestInterceptor 实际上就是处理传过来的用户名和密码，最后在HTTP请求头上加上Authorization头设置，用于生产者服务的BasicHttp验证

```java
package feign.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Util;
import java.nio.charset.Charset;

public class BasicAuthRequestInterceptor implements RequestInterceptor {
    private final String headerValue;

    public BasicAuthRequestInterceptor(String username, String password) {
        this(username, password, Util.ISO_8859_1);
    }

    public BasicAuthRequestInterceptor(String username, String password, Charset charset) {
        Util.checkNotNull(username, "username", new Object[0]);
        Util.checkNotNull(password, "password", new Object[0]);
        this.headerValue = "Basic " + base64Encode((username + ":" + password).getBytes(charset));
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.encode(bytes);
    }
	
    public void apply(RequestTemplate template) {
        //加入basicHttp用户信息
        template.header("Authorization", new String[]{this.headerValue});
    }
}
```

## 6.5 Feign对继承的支持

> Feign支持自定义api接口 用于FeignClient继承，但是api中的所用的请求的注解要和FeignClient中用的契约一直	

### 定义Api接口

```java
/**
 * Feign Client 支持集成接口的形式来扩展FeignClient的接口
 * 但是继承过来的接口 他的契约格式要按照FeignClient的契约格式来写
 */
public interface UserService {
    //SpringMVC契约格式  调用 服务生产者 提供的 /getUserNames 资源
//    @RequestMapping("/getUserNames")
//    public List<String> getUserNames();

    //Feign默认契约格式  调用 服务生产者 提供的 /getUserNames 资源
    @RequestLine("GET /getUserNames")
    public List<String> getUserNames();
}
```

### FeignClient可以集成api的接口进行扩展自己的方法

```java
/**
 * extends UserService 通过集成接口的方式来扩展自己
 */
@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserFeignClient extends UserService {
    //Feign默认的Client的请求写法
    //根据用户id获取用户名称
    @RequestLine("GET /user/{id}")
    public String getUserNameById(@Param("id") int id);

    //SpringMVC契约模式
//    @RequestMapping("/user/{id}")
//    public String getUserNameById(@PathVariable("id") int id);
}
```

**此时FeignCleint就具备了UserService中继承过来的方法了** 

## 6.6 Feign 对压缩的支持

> Feign支持对 请求和响应进行压缩

### 开启 请求和响应的压缩功能

```yaml
#开启Feign对与请求和响应的压缩
feign:
  compression:
    request:
      enabled: true
    response:
      enabled: true
```

### 更详细的配置

```yaml
#设置FeignRest调用配置
feign:
  #开启Feign对与请求和响应的压缩
  compression:
    request:
      enabled: true
      mime-types: "text/xml", "application/xml", "application/json"
      min-request-size: 2048
    response:
      enabled: true
```

## 6.7 Feign的日志配置

### Feign的日志级别

- NONE	无记录
- BASIC  只记录请求方法，URL，响应状态代码，执行时间
- HEADERS 记录基本信息，请求和响应标头
- FULL 记录请求和响应的头文件，正文和元数据

### 开启Debug日志级别

默认的Feign只对Debug级别提供显示，所以修改日志级别为debug

```yaml
logging:
  level:
    #要打印日志的 FeignClient 的包路径
    thread.consumer.feign: debug
```

### yaml配置Feign的日志显示的内容的级别

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

### java配置类配置，在feign配置类中加入Logger.level 配置

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

### 让Feign可以在INFO级别下显示

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

## 6.8 使用Feign构建多参数请求

### 例如生产者服务Get和Post接口方式

```java
@GetMapping("/get/user")
public String getUserNameByUserGET(User user){
    int id = user.getId();
    return userService.getUserNameById(id);
}
```

```java
@PostMapping("/post/user")
public String getUserNameByUserPOST(@RequestBody User user){
    int id = user.getId();
    return userService.getUserNameById(id);
}
```

### FeignClient实现方式   @RequestBody 只支持PostBody是json的形式 Get请求不支持

> Get方式的 参数 必须要加上 @RequestParam 注解

```java
/**
 * extends UserService 通过集成接口的方式来扩展自己
 */
@FeignClient(name = "thread-produce", configuration = FeignConfiguration.class)
public interface UserFeignClient extends UserService {
    //Feign默认的Client的请求写法
    //根据用户id获取用户名称
//    @RequestLine("GET /user/{id}")
//    public String getUserNameById(@Param("id") int id);

    //SpringMVC契约模式
    @GetMapping("/get/user")
    public String getUserNameByIdGET(@RequestParam int id, @RequestParam String name);

    @PostMapping("/post/user")
    public String getUserNameByIdPOST(@RequestBody User user);
}
```

## 6.9 使用 Feign 上传文件

> 上传主要代码在 Consumer 的 FileUploadCleint 和 FileUploadConfiguration类中

### 添加Feign对于上传文件的依赖

```xml
<!--Feign对于上传文件的依赖-->
<dependency>
    <groupId>io.github.openfeign.form</groupId>
    <artifactId>feign-form-spring</artifactId>
    <version>3.8.0</version>
</dependency>
<dependency>
    <groupId>io.github.openfeign.form</groupId>
    <artifactId>feign-form</artifactId>
    <version>3.8.0</version>
</dependency>
```

### SpringBoot配置上传文件大小无限制

```yaml
spring:
  servlet:
    multipart:
      max-file-size: -1 #设置上传文件大小无限制
```

### 生产者提供一个上传文件的接口，写法就是SpringMVC的上传接口的写法

```java
@Controller
public class FileUploadController {
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestPart MultipartFile file){
        String upload = fileUploadClient.upload(file);
        return upload;
    }
}
```

### 消费者 创建上传文件的FeignClient进行调用生产者

```java
@FeignClient(name = "file-upload", configuration = FileUploadClient.FileUploadConfiguration.class)
public interface FileUploadClient {
    //consumes设置提交类型是什么
    //这里 consumes = MediaType.MULTIPART_FORM_DATA_VALUE 设置上是formdata的方式
    //produces 设置返回类型是什么
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file")MultipartFile file);

    //定义FeignUpload的配置
    class FileUploadConfiguration{
        /**
         * 加载用于Spring上传的 Encoder
         * @return
         */
        @Bean
        public Encoder encoder(){
            return new SpringFormEncoder();
        }
    }
}
```

