package com.nakara.nacos.consumer.controller;

import com.nakara.microservice.service.pay.api.ServicePayApi;
import com.nakara.microservice.service.pay.required.api.ServicePayRequiredApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author NakarA
 * @since 2020/8/2
 */
@RestController
public class RestConsumerController {

//    实现远程调用 pay required 接口, 此接口间接调用了 pay 接口
    @org.apache.dubbo.config.annotation.Reference
    ServicePayRequiredApi servicePayRequiredApi;

    @GetMapping(value = "/pay/required")
    public String getServicePayRequiredApi () {
        String providerResult = servicePayRequiredApi.dubboServicePayRequiredService();
        return "通过 application 调用中间服务 pay required 的结果为: " + providerResult;
    }


    //    实现远程调用 dubbo 接口, 远程 RPC 调用
//    @org.apache.dubbo.config.annotation.Reference
//    ServicePayApi servicePayApi;
//
//    @GetMapping(value = "/pay")
//    public String getServicePayApi () {
//        String providerResult = servicePayApi.dubboServicePay();
//        return "远程调用 service pay dubboService pay 方法的结果: " + providerResult;
//    }


//    // 指定服务名
//    String serviceId = "nacos-restful-provider";
//
////    通过负载均衡来发现地址, 从服务发现中心拿到nacos-restful-provider服务, 通过负载均衡算法获取一个地址
//    @Autowired
//    LoadBalancerClient loadBalancerClient;
//
//    // 服务发现的服务调用
//    @GetMapping(value = "/nacos/consumer")
//    public String getNacos () {
//
//        // 远程调用
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 发现一个地址
//        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
//        URI uri = serviceInstance.getUri();
//        String res = restTemplate.getForObject(uri + "/nacos/provider", String.class);
//        System.out.println("服务 consumer 被调用, 返回的结果: " + res);
//        return "服务发现得到的数据: " + res;
//    }


    // 写死的服务调用
    @Value("${provider.address}")
    private String providerAddress;
    @GetMapping(value = "/service")
    public String service () {

        // 远程调用
        RestTemplate restTemplate = new RestTemplate();

        String providerResult = restTemplate.getForObject("http://" + providerAddress + "/get", String.class);
        return "consumer invoke | " + providerResult;
    }


}
