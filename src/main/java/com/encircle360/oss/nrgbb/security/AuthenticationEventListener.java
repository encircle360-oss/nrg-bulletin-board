package com.encircle360.oss.nrgbb.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.service.AuthorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final AuthorService authorService;

    @EventListener(AuthenticationSuccessEvent.class)
    public void authSuccess(AuthenticationSuccessEvent event) {
        Jwt principal = (Jwt) event.getAuthentication().getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        if (username == null || authorService.countByEmail(username) > 0) {
            return;
        }

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
