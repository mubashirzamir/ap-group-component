package com.group_component.master_gateway.controller;

import com.group_component.master_gateway.service.DeleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final DeleteService deleteService;

    public UserController(DeleteService deleteService) {
        this.deleteService = deleteService;
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable String email) {
        return this.deleteService.delete(email);
    }
}
