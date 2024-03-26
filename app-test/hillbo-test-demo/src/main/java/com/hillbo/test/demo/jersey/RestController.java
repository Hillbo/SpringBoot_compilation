package com.hillbo.test.demo.jersey;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest/test")
@Controller
public class RestController {

    @GET
    @Path("/jsonResult")
    @Produces(MediaType.APPLICATION_JSON)
    public String test1() {

        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("message", "success!");
        return result.toJSONString();
    }

}
