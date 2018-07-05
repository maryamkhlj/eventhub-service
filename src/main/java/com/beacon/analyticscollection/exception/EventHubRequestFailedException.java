package com.beacon.analyticscollection.exception;

public class EventHubRequestFailedException extends Exception {

    public EventHubRequestFailedException(String message) {
        super("Was unable to deliver the event to the event hub: " + message);
    }
}
