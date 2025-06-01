package com.example.backendBonosCorp.iam.interfaces.rest;


import com.example.backendBonosCorp.iam.domain.model.queries.GetAllUsersQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetRucByIdQuery;
import com.example.backendBonosCorp.iam.domain.model.queries.GetUserByRucQuery;
import com.example.backendBonosCorp.iam.domain.services.UserQueryService;
import com.example.backendBonosCorp.iam.interfaces.rest.resources.UserResource;
import com.example.backendBonosCorp.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
   private final UserQueryService userQueryService;

    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    @GetMapping("/{rucId}")
    public ResponseEntity<UserResource> getUserByRuc(@PathVariable String rucId) {
        var getUserByRucQuery = new GetUserByRucQuery(rucId);
        var user = userQueryService.handle(getUserByRucQuery);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}
