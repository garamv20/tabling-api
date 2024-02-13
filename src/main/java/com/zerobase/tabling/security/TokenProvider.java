package com.zerobase.tabling.security;

import com.zerobase.tabling.service.CustomerService;
import com.zerobase.tabling.service.ManagerService;
import com.zerobase.tabling.type.UserType;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000L * 60 * 60; // 1hour
    private static final String KEY_ROLES = "roles";
    
    private final CustomerService customerService;

    private final ManagerService managerService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성(발급)
     * @param email
     * @param userType
     * @return
     */
    public String generateToken(String email, UserType userType) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(KEY_ROLES, userType);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(expiredDate)     // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey)  // 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    /**
     * 토큰 유효성 검사
     * @param jwtToken
     * @return
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 인증 정보 조회
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        String userType = this.parseClaims(token).get(KEY_ROLES).toString();

        UserDetails userDetails = null;

        if (userType.equals("MANAGER")) {
            userDetails
                    = this.managerService.loadUserByUsername(this.getUsername(token));
        }else if (userType.equals("CUSTOMER")){
            userDetails
                    = this.customerService.loadUserByUsername(this.getUsername(token));
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
