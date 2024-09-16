package com.zerobase.tabling.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "STORE")
public class Store extends BaseEntity{
    /**
     * 매장 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 매니저 아이디
     */
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    /**
     * 매장 이름
     */
    private String name;

    /**
     * 매장 주소
     */
    private String address;

    /**
     * 매장 전화번호
     */
    private String phoneNumber;

    /**
     * 매장 설명
     */
    private String description;
}
