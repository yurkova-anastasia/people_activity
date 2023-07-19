package com.ku.users.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "authority_name")
    private String authorityName;
    @Column(name = "inserted_date_at_utc")
    private LocalDateTime insertedDateAtUtc;
    @Column(name = "updated_date_at_utc")
    private LocalDateTime updatedDateAtUtc;
    @ManyToMany
    @JoinTable(
        name = "role_authority_links",
        joinColumns = {@JoinColumn(name = "authority_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}
