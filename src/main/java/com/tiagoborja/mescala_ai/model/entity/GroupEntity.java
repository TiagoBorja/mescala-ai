package com.tiagoborja.mescala_ai.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "group_fk")
    private GroupEntity parentGroup;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupEntity> subGroups = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tenant_fk", nullable = false)
    private TenantEntity tenant;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupEntity> userGroups = new ArrayList<>();

    public GroupEntity() {
    }

    public GroupEntity(String name) {
        this.name = name;
    }

    private GroupEntity(Builder builder) {
        this.name = builder.name;
        this.parentGroup = builder.parentGroup;
        this.subGroups = builder.subGroups;
        this.tenant = builder.tenant;
        this.isActive = builder.isActive;
        this.userGroups = builder.userGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupEntity getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(GroupEntity parentGroup) {
        this.parentGroup = parentGroup;
    }

    public List<GroupEntity> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<GroupEntity> subGroups) {
        this.subGroups = subGroups;
    }

    public TenantEntity getTenant() {
        return tenant;
    }

    public void setTenant(TenantEntity tenant) {
        this.tenant = tenant;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    public List<UserGroupEntity> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroupEntity> userGroups) {
        this.userGroups = userGroups;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private GroupEntity parentGroup;
        private List<GroupEntity> subGroups;
        private TenantEntity tenant;
        private Boolean isActive;
        private List<UserGroupEntity> userGroups = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parentGroup(GroupEntity parentGroup) {
            this.parentGroup = parentGroup;
            return this;
        }

        public Builder subGroup(List<GroupEntity> subGroups) {
            this.subGroups = subGroups;
            return this;
        }

        public Builder tenant(TenantEntity tenant) {
            this.tenant = tenant;
            return this;
        }

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder userGroups(List<UserGroupEntity> userGroups) {
            this.userGroups = userGroups;
            return this;
        }

        public GroupEntity build() {
            return new GroupEntity(this);
        }
    }
}
