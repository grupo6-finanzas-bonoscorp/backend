package com.example.backendBonosCorp.iam.infrastructure.hashing.bcrypt;

import com.example.backendBonosCorp.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
