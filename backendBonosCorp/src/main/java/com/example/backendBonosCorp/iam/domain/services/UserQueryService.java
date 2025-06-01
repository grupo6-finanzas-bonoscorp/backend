package com.example.backendBonosCorp.iam.domain.services;

import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import com.example.backendBonosCorp.iam.domain.model.queries.GetAllUsersQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetRucByIdQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetUserByRucQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetRucByIdQuery query);
    Optional<User> handle(GetUserByRucQuery query);

}
