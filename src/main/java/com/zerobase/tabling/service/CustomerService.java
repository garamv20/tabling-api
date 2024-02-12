package com.zerobase.tabling.service;

import com.zerobase.tabling.domain.CustomerEntity;
import com.zerobase.tabling.dto.Auth;
import com.zerobase.tabling.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find email -> " + email));
    }
    public CustomerEntity register(Auth.SignUp customer) {
        boolean exists = this.customerRepository.existsByEmail(customer.getEmail());

        if (exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다");
        }
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        var result = this.customerRepository.save(customer.toCustomerEntity());
        return result;
    }

    public CustomerEntity authenticate(Auth.SignIn customer) {
        var user = this.customerRepository.findByEmail(customer.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if (!this.passwordEncoder.matches(customer.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }

}
