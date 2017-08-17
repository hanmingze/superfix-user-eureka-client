package com.zgkj.user.controller;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 韩明泽 on 2017/8/4.
 */
@RestController
public class DcController {
    @Autowired
    StringEncryptor stringEncryptor;
    @Autowired
    DiscoveryClient discoveryClient;
    @RequestMapping("jiaMi")
    public String jiaMi(){
        String hmz = stringEncryptor.encrypt("hmz");
        return hmz;
    }
    @GetMapping("dc")
    public String dc() throws InterruptedException {
        String services = "Services: " + discoveryClient.getServices();
        System.out.println("服务端得到请求的服务信息:"+services);
        return services;
    }
}
