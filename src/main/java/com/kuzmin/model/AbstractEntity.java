package com.kuzmin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
    @Version
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
