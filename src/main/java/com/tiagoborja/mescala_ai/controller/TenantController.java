package com.tiagoborja.mescala_ai.controller;

import com.tiagoborja.mescala_ai.mapper.TenantMapper;
import com.tiagoborja.mescala_ai.model.dto.request.TenantRequest;
import com.tiagoborja.mescala_ai.model.dto.response.TenantResponse;
import com.tiagoborja.mescala_ai.model.entity.TenantEntity;
import com.tiagoborja.mescala_ai.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenant() {
        List<TenantResponse> tenants = tenantService.getAllTenants();
        return ResponseEntity.status(HttpStatus.OK).body(tenants);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<TenantResponse> getTenantByExternalId(@PathVariable UUID externalId) {
        TenantEntity tenantFound = tenantService.getTenantByExternalId(externalId);
        TenantResponse response = TenantMapper.toResponse(tenantFound);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody TenantRequest request) {
        TenantEntity saved = tenantService.createTenant(request);
        TenantResponse response = TenantMapper.toResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/{externalId}")
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable UUID externalId,
                                                       @RequestBody TenantRequest request) {
        TenantEntity tenantFound = tenantService.updateTenant(externalId, request);
        TenantResponse response = TenantMapper.toResponse(tenantFound);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<TenantResponse> activateTenant(@PathVariable UUID externalId) {
        TenantEntity tenant = tenantService.activateTenant(externalId);
        return ResponseEntity.ok(TenantMapper.toResponse(tenant));
    }

    @PatchMapping("/{externalId}/deactivate")
    public ResponseEntity<TenantResponse> deactivateTenant(@PathVariable UUID externalId) {
        TenantEntity tenant = tenantService.deactivateTenant(externalId);
        return ResponseEntity.ok(TenantMapper.toResponse(tenant));
    }
}
