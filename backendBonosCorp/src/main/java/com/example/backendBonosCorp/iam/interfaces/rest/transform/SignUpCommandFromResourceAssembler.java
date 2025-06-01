package com.example.backendBonosCorp.iam.interfaces.rest.transform;


import com.example.backendBonosCorp.iam.domain.model.commands.SignUpCommand;
import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() !=null
                ? resource.roles().stream().map(Role::toRoleFromName).toList()
                : new ArrayList<Role>();
        return new SignUpCommand(resource.ruc(), resource.password(), roles);
    }
}
