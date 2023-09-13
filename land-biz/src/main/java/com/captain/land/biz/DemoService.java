package com.captain.land.biz;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by jackcaptain on 2017/12/8.
 */
public interface DemoService {
    public Integer getCount(Integer count);
}
