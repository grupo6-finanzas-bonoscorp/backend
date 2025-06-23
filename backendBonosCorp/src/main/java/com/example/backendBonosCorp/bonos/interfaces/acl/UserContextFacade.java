package com.example.backendBonosCorp.bonos.interfaces.acl;

import com.example.backendBonosCorp.iam.domain.model.queries.GetUserByRucQuery;
import com.example.backendBonosCorp.iam.domain.services.UserQueryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * UserContextFacade
 * <p>
 *     Este facade permite al contexto de bonos interactuar con el contexto de IAM
 *     para obtener información del usuario autenticado.
 *     Forma parte de la capa ACL (Anti-Corruption Layer).
 * </p>
 */
@Component
public class UserContextFacade {
    
    private final UserQueryService userQueryService;

    public UserContextFacade(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Obtiene el RUC del usuario autenticado actualmente
     * @return El RUC del usuario autenticado
     */
    public String getCurrentUserRuc() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Verifica si el usuario actual existe en el sistema
     * @return true si el usuario existe, false en caso contrario
     */
    public boolean currentUserExists() {
        var currentRuc = getCurrentUserRuc();
        var getUserQuery = new GetUserByRucQuery(currentRuc);
        var user = userQueryService.handle(getUserQuery);
        return user.isPresent();
    }

    /**
     * Verifica si el RUC proporcionado pertenece al usuario autenticado
     * @param ruc El RUC a verificar
     * @return true si el RUC coincide con el usuario autenticado
     */
    public boolean isCurrentUser(String ruc) {
        return getCurrentUserRuc().equals(ruc);
    }
} 