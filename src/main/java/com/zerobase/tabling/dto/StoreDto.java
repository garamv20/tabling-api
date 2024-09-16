package com.zerobase.tabling.dto;

import com.zerobase.tabling.entity.Manager;
import com.zerobase.tabling.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDto {
    private Manager manager;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .manager(store.getManager())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .build();
    }
}
