package com.zerobase.tabling.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    CUSTOMER("CUSTOMER"),
    MANAGER("MANAGER");

    private final String role;
}
