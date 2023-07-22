package com.shopping.cart.web;

import com.shopping.cart.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This will contain the rest endpoint for admin tasks.
 */

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/suspend-user")
    public void suspendUser() {
        adminService.suspendUser();
    }
}
