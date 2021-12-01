package com.azure.spring.samples.controller;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventGridPublishController {

    private static Logger logger = LoggerFactory.getLogger(TodoListController.class);

    @RequestMapping(value = "/api/eventgrid/publish", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publish() {
        logger.info("POST request access '/api/eventgrid/publish' path");
        try {
            EventGridPublisherClient<EventGridEvent> eventGridPublisherClient = new EventGridPublisherClientBuilder()
                    .endpoint("https://accountcreate.northeurope-1.eventgrid.azure.net/api/events")
                    .credential(new AzureKeyCredential("om/EDFlycJmtsZymrBqYj4T/RK67/mhEge5JyadiF8g="))
                    .buildEventGridEventPublisherClient();
            String body = "Account Info";
            EventGridEvent event = new EventGridEvent("New Account", "AccountCreated", BinaryData.fromObject(body), "0.1");
            eventGridPublisherClient.sendEvent(event);
            return new ResponseEntity<>("Event Sent", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }
}
