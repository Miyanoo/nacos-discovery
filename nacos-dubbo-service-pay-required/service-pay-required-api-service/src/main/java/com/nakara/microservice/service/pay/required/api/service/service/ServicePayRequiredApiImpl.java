package com.nakara.microservice.service.pay.required.api.service.service;

import com.nakara.microservice.service.pay.api.ServicePayApi;
import com.nakara.microservice.service.pay.required.api.ServicePayRequiredApi;

/**
 * @author NakarA
 * @since 2020/8/4
 */
@org.apache.dubbo.config.annotation.Service
public class ServicePayRequiredApiImpl implements ServicePayRequiredApi {

    @org.apache.dubbo.config.annotation.Reference
    ServicePayApi servicePayApi;

    public String dubboServicePayRequiredService () {

        String servicePayApiResult = servicePayApi.dubboServicePay();

        return "需要提供给 application 调用的 dubbo 接口, 同时自身也需要调用 pay 服务的服务1, 调用 pay 服务得到的数据为: " + servicePayApiResult;
    }
}
