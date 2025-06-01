package com.example.backendBonosCorp.iam.interfaces.rest.transform;


import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(entity.getId(), entity.getStringName());

    }
}
