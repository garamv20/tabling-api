package com.zerobase.tabling.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MANAGER_NOT_FOUND("매니저가 없습니다."),
    ALREADY_EXIST_STORE("이미 같은 이름의 매장이 존재합니다.")
    ;

    private final String description;
}
