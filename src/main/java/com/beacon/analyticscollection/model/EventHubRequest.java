package com.beacon.analyticscollection.model;

public class EventHubRequest {

    private String eventType;
    private String username;
    private String ipAddress;

    public EventHubRequest(EventType eventType, String username, String ipAddress) {
        this.eventType = eventType.name();
        this.username = username;
        this.ipAddress = ipAddress;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
