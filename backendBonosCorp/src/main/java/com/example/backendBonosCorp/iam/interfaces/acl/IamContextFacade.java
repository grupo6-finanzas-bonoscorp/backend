package com.example.backendBonosCorp.iam.interfaces.acl;


import com.example.backendBonosCorp.iam.domain.model.commands.SignUpCommand;
import com.example.backendBonosCorp.iam.domain.model.entities.Role;
import com.example.backendBonosCorp.iam.domain.model.queries.GetRucByIdQuery;
import com.example.backendBonosCorp.iam.domain.services.UserCommandService;
import com.example.backendBonosCorp.iam.domain.services.UserQueryService;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Creates a user with the given RUC and password.
     * @param ruc The RUC of the user.
     * @param password The password of the user.
     * @return The RUC of the created user. If the user could not be created, it returns an empty string.
     */
    public String createUser(String ruc, String password) {
        var signUpCommand = new SignUpCommand(ruc, password, List.of(Role.getDefaultRole()));
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getRuc();
    }

    /**
     * Creates a user with the given RUC, password and roles.
     * @param ruc The RUC of the user.
     * @param password The password of the user.
     * @param roleNames The names of the roles to assign to the user.
     * @return The RUC of the created user. If the user could not be created, it returns an empty string.
     */
    public String createUser(String ruc, String password, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(ruc, password, roles);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getRuc();
    }

    /**
     * Fetches the RUC of a user by its id.
     * @param rucId The id of the user.
     * @return The RUC of the user. If the user does not exist, it returns an empty string.
     */
    public String fetchRucById(Long rucId) {
        var getRucByIdQuery = new GetRucByIdQuery(rucId);
        var result = userQueryService.handle(getRucByIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getRuc();
    }

}