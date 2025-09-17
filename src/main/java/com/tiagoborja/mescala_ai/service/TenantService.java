package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.exception.NotFoundException;
import com.tiagoborja.mescala_ai.mapper.TenantMapper;
import com.tiagoborja.mescala_ai.model.dto.request.TenantRequest;
import com.tiagoborja.mescala_ai.model.dto.response.TenantResponse;
import com.tiagoborja.mescala_ai.model.entity.TenantEntity;
import com.tiagoborja.mescala_ai.repository.TenantRepository;
import com.tiagoborja.mescala_ai.validator.TenantValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantValidator validator;
    public TenantService(TenantRepository tenantRepository, TenantValidator validator) {
        this.tenantRepository = tenantRepository;
        this.validator = validator;
    }

    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantMapper::toResponse)
                .toList();
    }

    public TenantEntity getTenantByExternalId(UUID externalId) {
        return tenantRepository.findByExternalId(externalId)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public TenantEntity createTenant(TenantRequest request) {
        validator.validate(request);
        TenantEntity entity = TenantMapper.toEntity(request);
        return tenantRepository.save(entity);
    }

    @Transactional
    public TenantEntity updateTenant(UUID externalId, TenantRequest request) {
        validator.validate(request);
        TenantEntity tenant = tenantRepository.findByExternalId(externalId)
                .orElseThrow(NotFoundException::new);

        return tenantRepository.save(tenant);
    }
}
