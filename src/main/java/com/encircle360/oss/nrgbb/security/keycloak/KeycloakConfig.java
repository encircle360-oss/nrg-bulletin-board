package com.encircle360.oss.nrgbb.security.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Data;

@Data
@Configuration
@Profile("keycloak")
@ConfigurationProperties(prefix = "keycloak.admin")
public class KeycloakConfig {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder
                .builder()
                .serverUrl(this.serverUrl)
                .realm(this.realm)
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

}
