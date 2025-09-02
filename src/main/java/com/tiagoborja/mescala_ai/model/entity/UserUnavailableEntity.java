package com.tiagoborja.mescala_ai.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_unavailable")
public class UserUnavailableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private SystemUserEntity user;

    @Column(name = "unavailable_date", nullable = false)
    private LocalDate unavailableDate;

    @Column(length = 100)
    private String reason;

    public UserUnavailableEntity() {
    }

    public UserUnavailableEntity(Builder builder) {
        this.user = builder.user;
        this.unavailableDate = builder.unavailableDate;
        this.reason = builder.reason;
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

    public LocalDate getUnavailableDate() {
        return unavailableDate;
    }

    public void setUnavailableDate(LocalDate unavailableDate) {
        this.unavailableDate = unavailableDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SystemUserEntity user;
        private LocalDate unavailableDate;
        private String reason;

        public Builder user(SystemUserEntity user) {
            this.user = user;
            return this;
        }

        public Builder unavailableDate(LocalDate unavailableDate) {
            this.unavailableDate = unavailableDate;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public UserUnavailableEntity build() {
            return new UserUnavailableEntity(this);
        }
    }
}
