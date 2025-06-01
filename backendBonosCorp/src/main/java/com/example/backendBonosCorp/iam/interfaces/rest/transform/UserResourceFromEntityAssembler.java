package com.example.backendBonosCorp.iam.interfaces.rest.transform;

import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        var roles = entity.getRoles().stream().map(role -> role.getName().name()).toList();
        return new UserResource(entity.getId(), entity.getRuc(), roles);
    }
}