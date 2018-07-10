package com.beacon.analyticscollection.service;

import com.beacon.analyticscollection.model.EventHubRequest;
import com.beacon.analyticscollection.model.EventType;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AnalyticsCollectionService {

    @Value("${eventhub.connection-string}")
    private String connectionString;

    // async method will not wait to return to the controller. Exceptions will not be returned to the user, but should be dealt with
    // separately
    @Async
    public void forwardTrackedEvent(EventType actionType, String ipAddress, String username) throws EventHubException, IOException {

        System.out.println("Message being sent for: " +  actionType.toString());

        EventHubRequest payload = new EventHubRequest(actionType, ipAddress, username);
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        // instead of dealing with futures, just use the synchronous call and let the method run asynchronous instead
        // exceptions will be handled by BeaconCustomAsyncExceptionHandler
        final EventHubClient eventHubClient = EventHubClient.createSync(connectionString, executorService);

        try {

            byte[] payloadBytes = new GsonBuilder().create().toJson(payload).getBytes(Charset.defaultCharset());
            EventData sendEvent = EventData.create(payloadBytes);
            eventHubClient.sendSync(sendEvent);

            System.out.println(Instant.now() + ": Send Complete...");
        } finally {
            eventHubClient.closeSync();
            executorService.shutdown();
        }
    }
}
