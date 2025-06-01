package com.example.backendBonosCorp.iam.domain.model.aggregates;

import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AuditableAbstractAggregateRoot<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String ruc;
    //mi RUC es mi username, yo me identifico con mi RUC

    @Getter
    @NotBlank
    @Size(max = 120)
    private String password;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String ruc, String password) {
        this();
        this.ruc = ruc;
        this.password = password;
    }

    public User(String ruc, String password, List<Role> roles) {
        this(ruc, password);
        addRoles(roles);
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoles = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoles);
        return this;
    }
    public Long getId() {
        return id; // Si el campo es accesible directamente
    }
    public String getPassword() {
        return password;
    }

    public String getRuc() {
        return ruc;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}