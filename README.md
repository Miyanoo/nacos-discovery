
父工程: 使用 nacos-discovery

application 1: 使用 nacos-restful-consumer;

service1 微服务: 新建

service2 微服务: 新建

api 网关: 新建

### ### service2 微服务搭建

1. 由于 service 2 对外暴露 dubbo 协议接口, 既需要为 service1 提供服务, 又要向 application 提供服务, 考虑远程接口可能会被其他服务调用, 这里将 service2 的接口单独抽取出 api 工程, service2 微服务工程的结构如下: ![1596365239294](mdimg/service2%20%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

### 通过 dubbo 实现远程调用步骤

假设系统中有一个应用程序, 多个微服务系统, 同时微服务系统之间还存在调用关系

1. 使用 dubbo 的服务都需要引入 dubbo 依赖和 nacos 依赖

   ```
   <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-dubbo -->
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-dubbo</artifactId>
               <version>2.2.1.RELEASE</version>
           </dependency>
           
   <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
               <version>2.2.1.RELEASE</version>
           </dependency>
   ```

   

2. 完成被调用的微服务的 api 和 service, 同时 service 模块需要引入 api 模块, service 模块中完成 api 中方法的实现

```java
// api 中的接口
public interface ServicePayApi {
    String dubboServicePay () ;
}
```

```java
// service 中的实现类, 同时在 pom 文件中需要添加对 api 模块的依赖
@org.apache.dubbo.config.annotation.Service
public class ServicePayApiImpl implements ServicePayApi {

    public String dubboServicePay () {
        return "service pay api implementation";
    }
}
```



1. 在应用该程序中使用 @Reference 标签标注 api 中的方法 

   ```java
   //    实现远程调用 dubbo 接口, 远程 RPC 调用
   @org.apache.dubbo.config.annotation.Reference
   ServicePayApi servicePayApi;
   
   @GetMapping(value = "/pay")
   public String getServicePayApi () {
       String providerResult = servicePayApi.dubboServicePay();
       return "远程调用 service pay dubboService pay 方法的结果: " + providerResult;
   }
   ```