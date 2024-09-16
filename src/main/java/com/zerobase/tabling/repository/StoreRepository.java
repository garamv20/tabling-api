package com.zerobase.tabling.repository;

import com.zerobase.tabling.entity.Manager;
import com.zerobase.tabling.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByNameAndManager(String name, Manager manager);
}
