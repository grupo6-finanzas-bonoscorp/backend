package com.example.backendBonosCorp.iam.domain.model.commands;

import com.example.backendBonosCorp.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String ruc, String password, List<Role> roles) {
}
