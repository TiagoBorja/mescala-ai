package com.tiagoborja.mescala_ai.repository;

import com.tiagoborja.mescala_ai.model.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {

    Optional<TenantEntity> findByExternalId( UUID externalId);

    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
