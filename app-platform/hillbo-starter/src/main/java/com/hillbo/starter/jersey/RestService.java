package com.hillbo.starter.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/hillbo/rest")
public class RestService extends ResourceConfig {

    public RestService() {
        packages("com.hillbo");
    }

}
