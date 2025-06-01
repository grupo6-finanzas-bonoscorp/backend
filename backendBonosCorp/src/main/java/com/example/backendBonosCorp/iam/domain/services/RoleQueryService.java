package com.example.backendBonosCorp.iam.domain.services;

import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.domain.model.queries.GetAllRolesQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
