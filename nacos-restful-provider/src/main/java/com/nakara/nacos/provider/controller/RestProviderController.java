package com.nakara.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author NakarA
 * @since 2020/8/2
 */
@RestController
public class RestProviderController {

    // 指定服务名
    String serviceId = "nacos-restful-consumer";

    //    通过负载均衡来发现地址, 从服务发现中心拿到nacos-restful-provider服务, 通过负载均衡算法获取一个地址
    @Autowired
    LoadBalancerClient loadBalancerClient;

    // 服务发现的服务调用
    @GetMapping(value = "/nacos/provider")
    public String getNacos () {

        // 远程调用
        RestTemplate restTemplate = new RestTemplate();

        // 发现一个地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        URI uri = serviceInstance.getUri();
        String res = restTemplate.getForObject(uri + "/nacos/consumer", String.class);
        System.out.println("服务 provider 被调用, 返回的结果: " + res);
        return "服务发现得到的数据: " + res;
    }

//    暴露 RESTful 接口
    @GetMapping (value = "/get")
    public String get () {
        System.out.println("provider invoke.");
        return "provider invoke.";
    }

}
