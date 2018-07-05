package com.beacon.analyticscollection.rest;

import com.beacon.analyticscollection.model.EventHubRequest;
import com.beacon.analyticscollection.model.EventHubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="event-hub", url="${event-hub.location}")
public interface EventHubClient {

    @PostMapping("/event")
    EventHubResponse sendEvent(EventHubRequest event);
}
