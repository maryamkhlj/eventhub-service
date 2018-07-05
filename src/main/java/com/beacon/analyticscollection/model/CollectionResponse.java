package com.beacon.analyticscollection.model;

public class CollectionResponse {

    private int statusCode;
    private String message;

    public static CollectionResponse ok() {
        return new CollectionResponse();
    }

    public static CollectionResponse error(String message) {
        return new CollectionResponse(500);
    }

    private CollectionResponse() {
        this.statusCode = 200;
        this.message = "success";
    }

    private CollectionResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
