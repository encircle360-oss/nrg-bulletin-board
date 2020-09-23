package com.encircle360.oss.nrgbb.security;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import com.encircle360.oss.nrgbb.security.keycloak.KeycloakService;

import lombok.RequiredArgsConstructor;

@Profile("keycloak")
@RequiredArgsConstructor
public class ApplicationStartedEventListener {

    private final KeycloakService keycloakService;

    @EventListener(ApplicationStartedEvent.class)
    public void addRolesToKeycloak(ApplicationStartedEvent event) {
        keycloakService.addRolesToClient();
    }
}
