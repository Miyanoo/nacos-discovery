package com.nakara.microservice.service.pay.service;

import com.nakara.microservice.service.pay.api.ServicePayApi;

/**
 * @author NakarA
 * @since 2020/8/3
 */
@org.apache.dubbo.config.annotation.Service
public class ServicePayApiImpl implements ServicePayApi {

    public String dubboServicePay () {
        return "service pay api implementation";
    }

}
