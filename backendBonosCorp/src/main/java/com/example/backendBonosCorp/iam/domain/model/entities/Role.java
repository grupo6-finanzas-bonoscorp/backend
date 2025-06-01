package com.example.backendBonosCorp.iam.domain.model.entities;

import com.example.backendBonosCorp.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Getter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(unique = true, length = 20)
    private Roles name;

    public Roles getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Role() {}

    public Role(Roles name) {
        this.name = name;
    }

    public String getStringName() { return name.name(); }

    public static Role getDefaultRole() { return new Role(Roles.ROLE_ADMIN); }

    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) return List.of(getDefaultRole());
        return roles;
    }


}
