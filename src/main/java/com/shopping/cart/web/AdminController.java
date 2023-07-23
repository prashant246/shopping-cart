package com.shopping.cart.web;

import com.shopping.cart.datatypes.requests.SuspendUserRequest;
import com.shopping.cart.datatypes.responses.SuspendUserResponse;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.service.AdminService;
import com.shopping.cart.utils.SessionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shopping.cart.datatypes.Constants.X_SESSION_ID;

/**
 * This will contain the rest endpoint for admin tasks.
 */

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final SessionHandler sessionHandler;

    public AdminController(AdminService adminService, SessionHandler sessionHandler) {
        this.adminService = adminService;
        this.sessionHandler = sessionHandler;
    }

    /**
     * suspend of a use can be done through this api and only ADMINs can do that
     * @param sessionId
     * @param suspendUserRequest
     * @return
     */
    @PostMapping(value = "/suspend-user")
    public ResponseEntity suspendUser(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
                            @RequestBody SuspendUserRequest suspendUserRequest) {
        try {
            SuspendUserResponse response = adminService.suspendUser(suspendUserRequest, sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }
}
