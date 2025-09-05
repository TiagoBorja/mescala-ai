package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.mapper.TenantMapper;
import com.tiagoborja.mescala_ai.model.dto.request.TenantRequest;
import com.tiagoborja.mescala_ai.model.dto.response.TenantResponse;
import com.tiagoborja.mescala_ai.model.entity.TenantEntity;
import com.tiagoborja.mescala_ai.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantMapper::toResponse)
                .toList();
    }

    public TenantEntity getTenantByExternalId(UUID externalId) {
        return tenantRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    @Transactional
    public TenantEntity createTenant(TenantRequest request) {
        TenantEntity entity = TenantMapper.toEntity(request);
        return tenantRepository.save(entity);
    }

    @Transactional
    public TenantEntity updateTenant(UUID externalId, TenantRequest request) {
        TenantEntity tenant = tenantRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        if (request.name() != null) tenant.setName(request.name());
        if (request.phone() != null) tenant.setPhone(request.phone());
        if (request.email() != null) tenant.setEmail(request.email());
        if (request.address() != null) tenant.setAddress(request.address());

        return tenantRepository.save(tenant);
    }

    @Transactional
    public TenantEntity activateTenant(UUID externalId) {
        TenantEntity tenant = tenantRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setIsActive(true);
        return tenantRepository.save(tenant);
    }

    @Transactional
    public TenantEntity deactivateTenant(UUID externalId) {
        TenantEntity tenant = tenantRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setIsActive(false);
        return tenantRepository.save(tenant);
    }
}
