package com.zerobase.tabling.service;

import com.zerobase.tabling.entity.Customer;
import com.zerobase.tabling.dto.Auth;
import com.zerobase.tabling.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
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

    /**
     * 인증 정보 조회시 email로 해당 customer 조회
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find email -> " + email));
        return User.withUsername(customer.getEmail())
                .password(this.passwordEncoder.encode(customer.getPassword()))
                .roles(customer.getUserType().toString())
                .build();
    }

    /**
     * 고객 회원가입
     * @param customer
     * @return
     */
    public Customer register(Auth.SignUp customer) {
        boolean exists = this.customerRepository.existsByEmail(customer.getEmail());

        if (exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다");
        }
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        var result = this.customerRepository.save(customer.toCustomerEntity());
        return result;
    }

    /**
     * 고객 로그인 시 인증
     * @param customer
     * @return
     */
    public Customer authenticate(Auth.SignIn customer) {
        var user = this.customerRepository.findByEmail(customer.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if (!this.passwordEncoder.matches(customer.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }

}
