package com.beacon.analyticscollection.controller;

import com.beacon.analyticscollection.model.CollectionResponse;
import com.beacon.analyticscollection.model.EventType;
import com.beacon.analyticscollection.service.AnalyticsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("analytics")
public class AnalyticsCollectionController {

    @Autowired
    private AnalyticsCollectionService analyticsCollectionService;

    @GetMapping("/open/login")
    public CollectionResponse trackLogin(@RequestParam("username") String username, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.LOGIN, ipAddress, username);
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();
    }

    @GetMapping("login-success")
    public CollectionResponse trackLoginSuccess(HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        // TODO get username from auth principal

        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.LOGIN_SUCCESSFUL, ipAddress, "");
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();
    }

    @GetMapping("logout")
    public CollectionResponse trackLogout(HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        // TODO get username from auth principal

        try {
            analyticsCollectionService.forwardTrackedEvent(EventType.LOGOUT, ipAddress, "");
        } catch (Exception e) {
            return CollectionResponse.error(e.getMessage());
        }
        return CollectionResponse.ok();

    }

    // user has requested a password change by entering their email
    @GetMapping("/open/password/change-sent")
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
    @GetMapping("/open/password/change-email-clicked")
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
    @GetMapping("/open/password/change-completed")
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
