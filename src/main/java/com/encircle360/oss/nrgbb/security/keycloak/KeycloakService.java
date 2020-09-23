package com.encircle360.oss.nrgbb.security.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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

}
