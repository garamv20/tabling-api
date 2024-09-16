package com.zerobase.tabling.service;

import com.zerobase.tabling.entity.Manager;
import com.zerobase.tabling.entity.Store;
import com.zerobase.tabling.dto.CreateStore;
import com.zerobase.tabling.dto.StoreDto;
import com.zerobase.tabling.exception.TablingException;
import com.zerobase.tabling.repository.ManagerRepository;
import com.zerobase.tabling.repository.StoreRepository;
import com.zerobase.tabling.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    /**
     * 매장 등록
     * @param request 매장 정보, 매니저 아이디
     * @return
     */
    public StoreDto createStore(CreateStore.Request request) {
        Manager manager = this.managerRepository.findById(request.getManagerId())
                .orElseThrow(() -> new TablingException(ErrorCode.MANAGER_NOT_FOUND));

        if (this.storeRepository.existsByNameAndManager(request.getName(), manager)){
            throw new TablingException(ErrorCode.ALREADY_EXIST_STORE);
        }

        return StoreDto.from(this.storeRepository.save(Store.builder()
                        .manager(manager)
                        .name(request.getName())
                        .address(request.getAddress())
                        .phoneNumber(request.getPhoneNumber())
                        .description(request.getDescription())
                        .build()));
    }
}
