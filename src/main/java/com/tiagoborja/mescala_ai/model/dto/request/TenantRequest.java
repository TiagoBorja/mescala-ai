package com.tiagoborja.mescala_ai.model.dto.request;

import jakarta.validation.constraints.*;

public record TenantRequest(

        @NotBlank
        @Size(min = 1, max = 150)
        String name,

        @Pattern(regexp = "\\+?[0-9\\- ]{7,20}")
        String phone,

        @NotBlank
        @Size(min = 1, max = 150)
        @Email
        String email,

        @NotBlank
        @Size(min = 1, max = 150)
        String address,

        boolean isActive) {
}
