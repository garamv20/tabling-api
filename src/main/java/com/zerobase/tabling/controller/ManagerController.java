package com.zerobase.tabling.controller;

import com.zerobase.tabling.dto.Auth;
import com.zerobase.tabling.security.TokenProvider;
import com.zerobase.tabling.service.ManagerService;
import com.zerobase.tabling.type.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        var result = this.managerService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        var manager = this.managerService.authenticate(request);
        var token = this.tokenProvider.generateToken(manager.getEmail(), UserType.MANAGER);
        log.info("manager login -> " + request.getEmail());
        return ResponseEntity.ok(token);
    }
}
