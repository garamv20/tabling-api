package com.zerobase.tabling.service;

import com.zerobase.tabling.entity.Manager;
import com.zerobase.tabling.dto.Auth;
import com.zerobase.tabling.repository.ManagerRepository;
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
public class ManagerService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;

    /**
     * 인증정보 조회 시 email로 manager 조회
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Manager manager = this.managerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find email -> " + email));

        return User.withUsername(manager.getEmail())
                .password(this.passwordEncoder.encode(manager.getPassword()))
                .roles(manager.getUserType().getRole())
                .build();
    }

    /**
     * 매니저 회원가입
     * @param manager
     * @return
     */
    public Manager register(Auth.SignUp manager) {
        boolean exists = this.managerRepository.existsByEmail(manager.getEmail());

        if (exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다");
        }
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        var result = this.managerRepository.save(manager.toManagerEntity());
        return result;
    }

    /**
     * 매니저 로그인 시 인증
     * @param manager
     * @return
     */
    public Manager authenticate(Auth.SignIn manager) {
        var user = this.managerRepository.findByEmail(manager.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if (!this.passwordEncoder.matches(manager.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }
}
