package com.example.backendBonosCorp.iam.application.internal.outboundservices.tokens;

public interface TokenService {
    /**
     * Generates a token based on the provided RUC (Registro Único de Contribuyentes).
     *
     * @param ruc the RUC to generate the token for
     * @return the generated token
     */
    String generateToken(String ruc);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}