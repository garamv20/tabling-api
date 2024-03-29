package com.zerobase.tabling.controller;

import com.zerobase.tabling.dto.Auth;
import com.zerobase.tabling.security.TokenProvider;
import com.zerobase.tabling.service.CustomerService;
import com.zerobase.tabling.type.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        var result = this.customerService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        var customer = this.customerService.authenticate(request);
        var token = this.tokenProvider.generateToken(customer.getEmail(), UserType.CUSTOMER);
        log.info("customer login -> " + request.getEmail());
        return ResponseEntity.ok(token);
    }

}
