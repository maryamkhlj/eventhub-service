package com.beacon.analyticscollection.controller;

import com.beacon.analyticscollection.model.CollectionResponse;
import com.beacon.analyticscollection.model.EventType;
import com.beacon.analyticscollection.service.AnalyticsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("analytics")
public class AnalyticsCollectionController {

    @Autowired
    AnalyticsCollectionService analyticsCollectionService;

    @GetMapping("login")
    public CollectionResponse trackLogin(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.LOGIN, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();
    }

    @GetMapping("logout")
    public CollectionResponse trackLogout(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.LOGOUT, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();

    }

    // user clicks on forgot password button
    @GetMapping("/password/change")
    public CollectionResponse trackPasswordChange(HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.PASSWORD_CHANGE_REQUEST, ipAddress, "anonymous");
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();

    }

    // user has requested a password change by entering their email
    @GetMapping("/password/change-sent")
    public CollectionResponse trackPasswordChangeEmailSent(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.PASSWORD_CHANGE_SEND_EMAIL, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();

    }

    // user has clicked on the email in their inbox
    @GetMapping("/password/change-email-clicked")
    public CollectionResponse trackPasswordChangeEmailClicked(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.PASSWORD_CHANGE_CLICK_EMAIL, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();

    }

    // user has completed the password reset process by entering a new password
    @GetMapping("/password/change-completed")
    public CollectionResponse trackPasswordChangeCompleted(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.PASSWORD_CHANGE_COMPLETED, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();
    }

}
