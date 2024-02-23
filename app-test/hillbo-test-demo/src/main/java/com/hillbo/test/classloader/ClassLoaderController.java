package com.hillbo.test.classloader;

import com.alibaba.fastjson.JSONObject;
import com.hillbo.test.mybatisandplus.sdk.RedevelopSDK;
import com.hillbo.test.utils.SpringContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Slf4j
@Controller
@Path("/classLoader")
public class ClassLoaderController {

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    @GET
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON)
    public String loadJar(@QueryParam("path") String path) {

        JSONObject result = new JSONObject();
        try {

            classLoaderConfig.loadJarByPath("demo", path);
            result.put("status", 200);
            result.put("message", "loadSuccess");
            return result.toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 500);
            result.put("message", "loadFailed");
            return result.toJSONString();
        }
    }

    @GET
    @Path("/execute")
    @Produces(MediaType.APPLICATION_JSON)
    public String execute() {

        JSONObject result = new JSONObject();
        try {

            RedevelopSDK redevelopSDK = SpringContextHolderUtil.getBean("sDUserService");
            redevelopSDK.execute();

            result.put("status", 200);
            result.put("message", "executeSuccess");
            return result.toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 500);
            result.put("message", "executeFailed");
            return result.toJSONString();
        }
    }

}
