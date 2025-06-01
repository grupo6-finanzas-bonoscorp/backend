package com.example.backendBonosCorp.iam.application.internal.commandservices;

import com.example.backendBonosCorp.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.backendBonosCorp.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.backendBonosCorp.iam.domain.model.aggregates.User;
import com.example.backendBonosCorp.iam.domain.model.commands.SignInCommand;
import com.example.backendBonosCorp.iam.domain.model.commands.SignUpCommand;
import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.domain.model.valueobjects.Roles;
import com.example.backendBonosCorp.iam.domain.services.UserCommandService;
import com.example.backendBonosCorp.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.backendBonosCorp.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.backendBonosCorp.shared.application.exceptions.InvalidValueException;
import com.example.backendBonosCorp.shared.application.exceptions.ResourceAlreadyException;
import com.example.backendBonosCorp.shared.application.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByRuc(command.ruc()))
            throw new ResourceAlreadyException("RUC already exists");

        List<Role> rolesList = new ArrayList<>();

        if (command.roles().isEmpty()) {
            var role = roleRepository.findByName(Roles.ROLE_ADMIN);
            if (role.isPresent()) {
                rolesList.add(role.get());
            }
        } else {
            for (Role role : command.roles()) {
                // Suponiendo que Role tiene un campo 'name' que se puede acceder
                var foundRole = roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
                rolesList.add(foundRole);
            }
        }

        var user = new User(command.ruc(), hashingService.encode(command.password()), rolesList);
        userRepository.save(user);
        return userRepository.findByRuc(command.ruc());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByRuc(command.ruc());
        if (user.isEmpty()) throw new ResourceNotFoundException("User not found");

        String userPassword = user.get().getPassword();
        if (!hashingService.matches(command.password(), userPassword))
            throw new InvalidValueException("Invalid password");

        var currentUser = user.get();
        String userRuc = currentUser.getRuc();
        var token = tokenService.generateToken(userRuc);
        return Optional.of(ImmutablePair.of(currentUser, token));
    }
}