### 生产者服务
集成SpringDataJPA 做dao层 启动自动创建数据表
写了一个查询用户数据的接口
url : http://localhost:8081/getUserInfo/1

### 消费者服务
通过RestHttp方式请求生产者的服务 获取用户信息来使用
消费者的url： http://localhost:8080/findUser

### 此种方式的缺点
通过RestHTTP 请求生产者 url里的端口号和ip都写死在代码中即便是 提取到配置文件中也需要修改
此时就需要注册中心来管理这些服务管理 提供自动上线和如果服务不可用注销等功能

