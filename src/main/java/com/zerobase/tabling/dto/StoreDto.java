package com.zerobase.tabling.dto;

import com.zerobase.tabling.domain.ManagerEntity;
import com.zerobase.tabling.domain.StoreEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDto {
    private ManagerEntity manager;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;

    public static StoreDto from(StoreEntity store) {
        return StoreDto.builder()
                .manager(store.getManager())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .build();
    }
}
