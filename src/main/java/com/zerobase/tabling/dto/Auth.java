package com.zerobase.tabling.dto;

import com.zerobase.tabling.domain.CustomerEntity;
import com.zerobase.tabling.type.UserType;
import lombok.Data;

public class Auth {

    @Data
    public static class SignIn{
        private String email;
        private String password;
    }

    @Data
    public static class SignUp{
        private String email;
        private String username;
        private String password;

        public CustomerEntity toCustomerEntity() {
            return CustomerEntity.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(this.password)
                    .userType(UserType.CUSTOMER)
                    .build();
        }

    }
}
