package com.hillbo.test.redevelop.dao;

import com.hillbo.test.redevelop.pojo.SDUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SDUserMapper {

    void insertUser(SDUser sdUser);

}
