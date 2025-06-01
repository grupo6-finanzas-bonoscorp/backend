package com.example.backendBonosCorp.iam.interfaces.rest.transform;


import com.example.backendBonosCorp.iam.domain.model.commands.SignInCommand;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.ruc(),
                resource.password());
    }
}
