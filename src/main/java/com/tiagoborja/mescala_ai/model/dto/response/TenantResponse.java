package com.tiagoborja.mescala_ai.model.dto.response;

import com.tiagoborja.mescala_ai.model.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

public record TenantResponse(UUID externalId,
                             String name,
                             String phone,
                             String email,
                             String address,
                             boolean isActive,
                             List<GroupEntity> groups)
{
}
