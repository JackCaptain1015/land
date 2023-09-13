package com.captain.land.biz;

import com.captain.odps.mapper.OdpsDemoMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by jackcaptain on 2017/12/8.
 */
@Service
public class DemoServiceImpl implements DemoService{
//    @Resource
    private OdpsDemoMapper odpsDemoMapper;

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Integer getCount(Integer count) {
//        return odpsDemoMapper.getCount();
        return 1+count;
    }
}
