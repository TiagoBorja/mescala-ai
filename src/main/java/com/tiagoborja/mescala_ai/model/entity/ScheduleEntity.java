package com.tiagoborja.mescala_ai.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "schedules")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk")
    private SystemUserEntity user;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_fk")
    private GroupEntity group;

    public ScheduleEntity() {
    }

    public ScheduleEntity(Builder builder) {
        this.user = builder.user;
        this.scheduledDate = builder.scheduledDate;
        this.group = builder.group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUserEntity getUser() {
        return user;
    }

    public void setUser(SystemUserEntity user) {
        this.user = user;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SystemUserEntity user;
        private LocalDate scheduledDate;
        private GroupEntity group;

        public Builder user(SystemUserEntity user) {
            this.user = user;
            return this;
        }

        public Builder scheduledDate(LocalDate scheduledDate) {
            this.scheduledDate = scheduledDate;
            return this;
        }

        public Builder group(GroupEntity group) {
            this.group = group;
            return this;
        }

        public ScheduleEntity build() {
            return new ScheduleEntity(this);
        }
    }
}
