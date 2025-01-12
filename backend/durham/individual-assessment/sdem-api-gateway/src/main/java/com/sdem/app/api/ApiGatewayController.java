package com.sdem.app.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayController {

    @RequestMapping
    public String testApiGateway(){
        return "Welcome, Hello World";
    }
}
