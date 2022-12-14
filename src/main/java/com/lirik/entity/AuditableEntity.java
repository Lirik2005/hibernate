package com.lirik.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
