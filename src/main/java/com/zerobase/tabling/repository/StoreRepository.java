package com.zerobase.tabling.repository;

import com.zerobase.tabling.domain.ManagerEntity;
import com.zerobase.tabling.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByNameAndManager(String name, ManagerEntity manager);
}
