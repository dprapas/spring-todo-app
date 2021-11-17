package com.azure.spring.samples.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final MessageProperties properties;

    public MessageController(MessageProperties properties) {
        this.properties = properties;
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getMessage() {
        return "Message: " + properties.getMessage();
    }
}
