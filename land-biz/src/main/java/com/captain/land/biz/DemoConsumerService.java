package com.captain.land.biz;

import com.captain.land.biz.param.PostParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="land-provider")
public interface DemoConsumerService {
    /**
     * 这写的是provider中的/demo/count请求路由
     * @param count
     * @return
     */
    @GetMapping("/demo/count")
    Integer getCount(@RequestParam("count") Integer count);

    /**
     * 这里的DTO本来是要用provider提供的jar包的，但是这里为了方便，直接
     * 用相同的类
     * @param count
     * @return
     */
    @PostMapping("/demo/postCount")
    PostParam postCount(@RequestBody PostParam param);
}
