package com.hillbo.test.mybatisandplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hillbo.test.mybatisandplus.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void insertUser(User user);

    List<User> queryAllUsers(Page page);

}
