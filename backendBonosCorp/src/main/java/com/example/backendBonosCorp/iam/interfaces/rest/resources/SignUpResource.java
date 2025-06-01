package com.example.backendBonosCorp.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String ruc, String password, List<String> roles) {
}
