package com.example.backendBonosCorp.iam.domain.model.queries;

import com.example.backendBonosCorp.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
