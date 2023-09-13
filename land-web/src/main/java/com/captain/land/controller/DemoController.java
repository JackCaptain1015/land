package com.captain.land.controller;

import com.captain.land.biz.DemoConsumerService;
import com.captain.land.biz.DemoService;
import com.captain.land.biz.param.PostParam;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by jackcaptain on 2017/12/8.
 */
@RestController
public class DemoController {
    @Resource
    private DemoService demoService;

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DemoConsumerService demoConsumerService;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        String forObject = restTemplate.getForObject("http://land-provider/echo/" + str, String.class);
        return forObject;
    }

    @GetMapping("/demo/count")
    public Integer count(Integer count){
        Integer count1 = demoConsumerService.getCount(count);
        return count1;
    }

    @GetMapping("/demo/postCount")
    public PostParam postCount(){
        PostParam param = new PostParam();
        param.setStr("this consumer");
        PostParam postParam = demoConsumerService.postCount(param);
        return postParam;
    }

    public Integer getCount(){
//        return demoService.getCount();
        return 0;
    }
}
