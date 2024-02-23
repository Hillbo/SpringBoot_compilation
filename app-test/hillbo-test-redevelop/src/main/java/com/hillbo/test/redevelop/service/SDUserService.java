package com.hillbo.test.redevelop.service;

import com.hillbo.test.mybatisandplus.sdk.RedevelopSDK;
import com.hillbo.test.redevelop.dao.SDUserMapper;
import com.hillbo.test.redevelop.pojo.SDUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SDUserService implements RedevelopSDK {

    @Autowired
    private SDUserMapper sdUserMapper;

    @Override
    public void execute() {
        log.info("--------customize execute start--------");
        SDUser sdUser = new SDUser();
        sdUser.setAge(30);
        sdUser.setEmail("hillbo.zhao@foxmail.com");
        sdUser.setName("hillbo");
        sdUserMapper.insertUser(sdUser);
        log.info("--------customize execute end--------");
    }

//    @Override
//    public void execute() {
//        log.info("--------customize execute start--------");
//        SDUser sdUser = new SDUser();
//        sdUser.setAge(100);
//        sdUser.setEmail("hillbo.zhao@foxmail.com");
//        sdUser.setName("hillbo");
//        sdUserMapper.insertUser(sdUser);
//        log.info("--------customize execute end--------");
//    }

}
