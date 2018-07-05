package com.beacon.analyticscollection.service;

import com.beacon.analyticscollection.exception.EventHubRequestFailedException;
import com.beacon.analyticscollection.model.EventHubRequest;
import com.beacon.analyticscollection.model.EventType;
import com.beacon.analyticscollection.rest.EventHubClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsCollectionService {


    @Autowired
    private EventHubClient eventHubClient;

    @Async
    public void forwardTrackedEvent(EventType actionType, String ipAddress, String username) throws EventHubRequestFailedException{

        EventHubRequest request = new EventHubRequest(actionType, ipAddress, username);
        try {
            eventHubClient.sendEvent(request);
        }

        catch (FeignException e) {
            throw new EventHubRequestFailedException(e.getMessage());
        }

        // make a web request to the event hub service
    }

}
