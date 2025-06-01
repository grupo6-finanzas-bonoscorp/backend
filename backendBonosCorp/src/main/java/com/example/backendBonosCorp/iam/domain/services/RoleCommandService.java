package com.example.backendBonosCorp.iam.domain.services;

import com.example.backendBonosCorp.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
