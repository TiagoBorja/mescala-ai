package com.tiagoborja.mescala_ai.model.entity;

import com.tiagoborja.mescala_ai.UserGroupIdEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_groups")
public class UserGroupEntity {

    @EmbeddedId
    private UserGroupIdEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private SystemUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_fk", nullable = false)
    private GroupEntity group;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive;

    public UserGroupEntity() {
    }

    public UserGroupEntity(SystemUserEntity user, GroupEntity group, Boolean isActive) {
        this.user = user;
        this.group = group;
        this.isActive = isActive;
    }

    public SystemUserEntity getUser() {
        return user;
    }

    public void setUser(SystemUserEntity user) {
        this.user = user;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
