package com.zerobase.tabling.controller;

import com.zerobase.tabling.dto.CreateStore;
import com.zerobase.tabling.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/m/create")
    @PreAuthorize("hasRole('MANAGER')")
    public CreateStore.Response createStore(@RequestBody CreateStore.Request request) {
        return CreateStore.Response.from(this.storeService.createStore(request));
    }

}
