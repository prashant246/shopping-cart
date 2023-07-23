package com.shopping.cart.web;

import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.datatypes.responses.CreateUserResponse;
import com.shopping.cart.datatypes.responses.GetUserResponse;
import com.shopping.cart.datatypes.responses.LoginResponse;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shopping.cart.datatypes.Constants.X_SESSION_ID;

/**
 * this contains the rest endpoints related to user onboarding
 */

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * API to create the user. Role can be passed as ADMIN or CUSTOMER
     * @param createOrUpdateUserRequest
     * @return - the session id of the user created
     */
    @PostMapping
    public ResponseEntity create(@RequestBody CreateOrUpdateUserRequest createOrUpdateUserRequest) {
        try {
            CreateUserResponse userResponse = userService.create(createOrUpdateUserRequest);
            return new ResponseEntity(userResponse, HttpStatus.CREATED);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    /**
     * update the user details
     * @param sessionId
     * @param createOrUpdateUserRequest
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
            @RequestBody CreateOrUpdateUserRequest createOrUpdateUserRequest) {
        try {
            userService.update(createOrUpdateUserRequest, sessionId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    /**
     * Login API to login and generate a session id
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = userService.login(request);
            return new ResponseEntity(loginResponse, HttpStatus.ACCEPTED);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    /**
     * logout api to logout, it will invalidate the session
     * @param sessionId
     * @return
     */
    @PutMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId) {
        try {
            userService.logout(sessionId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    /**
     * Get basic details of user
     * @param sessionId
     * @return
     */
    @GetMapping
    public ResponseEntity get(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId) {
        try {
            GetUserResponse response = userService.get(sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }

    }
}
