package com.tiagoborja.mescala_ai.model.dto.request;

public record TenantRequest(String name,
                            String phone,
                            String email,
                            String address,
                            boolean isActive) {
}
