package com.zerobase.tabling.repository;

import com.zerobase.tabling.domain.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    Optional<ManagerEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
