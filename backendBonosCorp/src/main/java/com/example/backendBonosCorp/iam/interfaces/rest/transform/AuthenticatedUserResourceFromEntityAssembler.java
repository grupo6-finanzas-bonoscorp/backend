package com.example.backendBonosCorp.iam.interfaces.rest.transform;


import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String token) {

        return new AuthenticatedUserResource(entity.getId(), entity.getRuc(), token);
    }
}
