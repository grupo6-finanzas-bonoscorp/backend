package com.example.backendBonosCorp.iam.application.internal.queryservices;

import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import com.example.backendBonosCorp.iam.domain.model.queries.GetAllUsersQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetRucByIdQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetUserByRucQuery;
import com.example.backendBonosCorp.iam.domain.services.UserQueryService;
import com.example.backendBonosCorp.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetRucByIdQuery query) {
        return userRepository.findById(query.rucId());
    }

    @Override
    public Optional<User> handle(GetUserByRucQuery query) {
        return userRepository.findByRuc(query.ruc());
    }

}
