package com.captain.land.controller;

import com.captain.land.biz.DemoService;
import com.captain.land.biz.param.PostParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by jackcaptain on 2017/12/8.
 * provider
 */
@RestController
public class DemoController {
    @Resource
    private DemoService demoService;

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        String s = str + "_provider";
        System.out.println(s);
        return s;
    }

    @GetMapping("/demo/count")
    public Integer count(Integer count){
        Integer count1 = demoService.getCount(count);
        return count1;
    }

    @PostMapping("/demo/postCount")
    public PostParam postCount(@RequestBody PostParam param){
        param.setStr(param.getStr() +"_provider");
        return param;
    }



    public Integer getCount(){
//        return demoService.getCount();
        return null;
    }
}
