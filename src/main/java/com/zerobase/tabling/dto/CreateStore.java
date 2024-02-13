package com.zerobase.tabling.dto;

import com.sun.istack.NotNull;
import lombok.*;

public class CreateStore {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long managerId;

        @NotNull
        private String name;

        @NotNull
        private String address;

        @NotNull
        private String description;

        @NotNull
        private String phoneNumber;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long managerId;
        private String name;

        public static Response from(StoreDto storeDto) {
            return Response.builder()
                    .managerId(storeDto.getManager().getId())
                    .name(storeDto.getName())
                    .build();
        }
    }
}
