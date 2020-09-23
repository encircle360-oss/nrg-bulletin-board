package com.encircle360.oss.nrgbb.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.service.AuthorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final AuthorService authorService;

    @EventListener(AuthenticationSuccessEvent.class)
    public void authSuccess(AuthenticationSuccessEvent event) {
        Jwt principal = (Jwt) event.getAuthentication().getPrincipal();
        if (!principal.containsClaim("preferred_username")) {
            return;
        }

        String username = principal.getClaimAsString("preferred_username");

        // todo add check if token contains id of user
        if (username == null || authorService.countByEmail(username) > 0) {
            return;
        }

        // if user is not found in database create with data from JWT
        Author author = Author
            .builder()
            .name(username)
            .email(username)
            .active(true)
            .archived(false)
            .build();

        authorService.save(author);
    }
}
