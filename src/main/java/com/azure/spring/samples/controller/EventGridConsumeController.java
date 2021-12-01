package com.azure.spring.samples.controller;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationResponse;
import com.azure.spring.samples.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventGridConsumeController {

    private static Logger logger = LoggerFactory.getLogger(TodoListController.class);

    @RequestMapping(value = "/api/eventgrid/consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> consume(@RequestHeader("aeg-event-type") String eventType, @RequestBody String eventGridEventJsonData) {
        logger.info("POST request access '/api/eventgrid/consume' path");
        try {
            // Place a breakpoint and copy the validation URL
            // Use the validation URL in the browser to validate the subscription creation
            if(eventType.equals("SubscriptionValidation")) {
                List<EventGridEvent> eventGridEvents = EventGridEvent.fromString(eventGridEventJsonData);
                SubscriptionValidationEventData subValidationEvtData = eventGridEvents.get(0).getData().toObject(SubscriptionValidationEventData.class);
                SubscriptionValidationResponse response = new SubscriptionValidationResponse();
                response.setValidationResponse(subValidationEvtData.getValidationCode());
                return new ResponseEntity<String>(response.getValidationResponse(), HttpStatus.OK);
            }

            List<EventGridEvent> eventGridEvents = EventGridEvent.fromString(eventGridEventJsonData);
            for(EventGridEvent eventGridEvent: eventGridEvents) {
                logger.info("event " + eventGridEvent.getData());
            }
            return new ResponseEntity<>("Event Consumed: " + eventGridEvents.size(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }
}
