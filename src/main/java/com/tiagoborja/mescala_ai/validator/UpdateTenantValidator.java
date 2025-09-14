package com.tiagoborja.mescala_ai.validator;

import com.tiagoborja.mescala_ai.exception.AlreadyExistsException;
import com.tiagoborja.mescala_ai.model.dto.request.TenantRequest;
import com.tiagoborja.mescala_ai.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTenantValidator {

    private final TenantRepository tenantRepository;

    public UpdateTenantValidator(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public void validate(TenantRequest request) {
        checkExistsByField("name", request.name(), tenantRepository.existsByName(request.name()));
        checkExistsByField("email", request.email(), tenantRepository.existsByEmail(request.email()));
    }

    private void checkExistsByField(String field, String value, boolean exists) {
        if (exists) {
            String message = new StringBuilder()
                    .append("Tenant with ")
                    .append(field)
                    .append(" '")
                    .append(value)
                    .append("' already exists")
                    .toString();
            throw new AlreadyExistsException(message);
        }
    }
}
