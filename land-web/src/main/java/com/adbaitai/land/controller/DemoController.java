package com.adbaitai.land.controller;

import com.adbaitai.land.biz.DemoService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by jackcaptain on 2017/12/8.
 */
@Controller
public class DemoController {
    @Resource
    private DemoService demoService;

    public Integer getCount(){
        return demoService.getCount();
    }
}
