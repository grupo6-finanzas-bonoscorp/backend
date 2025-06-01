package com.example.backendBonosCorp.iam.infrastructure.persistence.jpa.repositories;

import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRuc(String ruc);
    boolean existsByRuc(String ruc);
}
