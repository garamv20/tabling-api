package com.zerobase.tabling.dto;

import com.zerobase.tabling.entity.Customer;
import com.zerobase.tabling.entity.Manager;
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

        public Customer toCustomerEntity() {
            return Customer.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(this.password)
                    .userType(UserType.CUSTOMER)
                    .build();
        }

        public Manager toManagerEntity() {
            return Manager.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(this.password)
                    .userType(UserType.MANAGER)
                    .build();
        }

    }
}
