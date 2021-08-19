## 启动步骤

### 1. 启动EureakServer

**查看Eureka 注册中心访问地址：**

http://localhost:777 

### 2.启动 多实例 Produce 生产者服务  默认写了两个生产者的配置信息 

**生产者提供的服务信息访问地址：**   

http://localhost:8081/getUserNames	

http://localhost:8082/getUserNames

**打包成jar包形式，分别启动两个produce：**

> java -jar .\Produce-1.0-SNAPSHOT.jar --spring.profiles.active=produce
>
> java -jar .\Produce-1.0-SNAPSHOT.jar --spring.profiles.active=produce2

### 3. 启动 Consumer消费者服务

**消费者获取生产者信息访问地址：**

http://localhost:8080/hello

**获取当前请求的生产者实例信息：**

http://localhost:8080/getProduce

