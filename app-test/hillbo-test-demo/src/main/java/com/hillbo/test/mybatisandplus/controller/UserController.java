package com.hillbo.test.mybatisandplus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hillbo.test.mybatisandplus.dao.UserMapper;
import com.hillbo.test.mybatisandplus.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser() {

        JSONObject result = new JSONObject();

        try {
            User user = new User();
            user.setAge(28);
            user.setName("hillbo");
            user.setEmail("hillbo.zhao@foxmail.com");

            System.out.println("mapper insert user");
            userMapper.insert(user);

            System.out.println("mapper xml insert user");
            userMapper.insertUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("----------------------");
            e.printStackTrace();
        }

        result.put("status", 200);
        result.put("message", "add success");
        return result.toJSONString();
    }

    @GET
    @Path("/queryMapperPage")
    @Produces(MediaType.APPLICATION_JSON)
    public String queryMapperUser() {

        JSONObject result = new JSONObject();

        try {
            Page<User> page = new Page<>(1, 10, true);
            Page<User> pageUser = userMapper.selectPage(page, null);
            return JSONObject.toJSONString(pageUser);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "500");
            result.put("message", "queryMapperPageError");
            return result.toJSONString();
        }
    }

    @GET
    @Path("/queryXmlPage")
    @Produces(MediaType.APPLICATION_JSON)
    public String queryXmlUser() {

        JSONObject result = new JSONObject();

        try {
            Page<User> page = new Page<>(2, 10, true);
            List<User> users = userMapper.queryAllUsers(page);
            return JSONObject.toJSONString(users);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "500");
            result.put("message", "queryXmlPageError");
            return result.toJSONString();
        }
    }

}
