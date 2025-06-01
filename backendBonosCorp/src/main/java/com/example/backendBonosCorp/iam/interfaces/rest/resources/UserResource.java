package com.example.backendBonosCorp.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String ruc, List<String> roles) {
}
