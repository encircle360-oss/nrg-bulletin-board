package com.encircle360.oss.nrgbb.security.keycloak;

import javax.ws.rs.NotFoundException;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.encircle360.oss.nrgbb.security.Roles;

import lombok.RequiredArgsConstructor;

@Service
@Profile("keycloak")
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${resourceServer.resourceId}")
    private String clientId;

    public void addRolesToClient() {
        ClientsResource clientsResource = keycloak.realm(this.realm).clients();
        ClientRepresentation clientRepresentation = clientsResource.findByClientId(clientId).stream().findFirst().orElse(null);

        if (clientRepresentation == null) {
            return;
        }

        ClientResource clientResource = clientsResource.get(clientRepresentation.getId());
        RolesResource rolesResource = clientResource.roles();

        for (String role : Roles.allRoles()) {
            role = Roles.cleanRole(role);
            RoleResource roleResource = rolesResource.get(role);
            RoleRepresentation roleRepresentation = null;
            try {
                roleRepresentation = roleResource.toRepresentation();
            } catch (NotFoundException notFoundException) {

            }

            if (roleRepresentation != null) {
                continue;
            }
            roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(role);
            roleRepresentation.setClientRole(true);
            rolesResource.create(roleRepresentation);
        }
    }
}
