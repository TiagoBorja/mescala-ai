package com.tiagoborja.mescala_ai.mapper;

import com.tiagoborja.mescala_ai.model.dto.request.TenantRequest;
import com.tiagoborja.mescala_ai.model.dto.response.TenantResponse;
import com.tiagoborja.mescala_ai.model.entity.TenantEntity;

import java.util.List;

public class TenantMapper {
    public static TenantEntity toEntity(TenantRequest request) {
        return TenantEntity.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public static TenantResponse toResponse(TenantEntity entity) {
        return new TenantResponse(
                entity.getExternalId(),
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getIsActive(),
                List.of()
        );
    }
}
