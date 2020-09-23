package com.encircle360.oss.nrgbb.controller;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encircle360.oss.nrgbb.security.Roles;

@RestController
@Profile("insecure")
@RequestMapping("/roles")
public class RolesController {

    @GetMapping("")
    public ResponseEntity<List<String>> roles() {
        return ResponseEntity.status(HttpStatus.OK).body(Roles.allRoles());
    }
}
