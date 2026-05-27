package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.security.AppUserDetails;
import edu.ban7.demo26cdamns.service.AppUserService;
import edu.ban7.demo26cdamns.service.IAppUserService;
import edu.ban7.demo26cdamns.view.AppUserView;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final IAppUserService userService;
    private final AuthenticationProvider authenticationProvider;


    @PostMapping("/sign-in")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> signIn(
            @RequestBody @Validated(AppUser.OnCreate.class) AppUser userToInsert) {

        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {

        try {
            AppUserDetails appUser = (AppUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()))
                    .getPrincipal();
// HERITAGE
//            String role = appUser.getDirecteur() != null
//                    ? "directeur"
//                    : (appUser.getTechnicien() != null
//                        ? "technicien"
//                        : "client")

            String jwt = Jwts.builder()
                    .setSubject(user.getEmail())
                    .addClaims(Map.of("role", appUser.getUser().getRole().getName()))
// PLUSIEURS ROLES
//                    .addClaims(Map.of("roles", appUser.getUser().getRoles().stream()
//                            .map(r -> r.getName().name())
//                            .collect(Collectors.joining(","))))

                    .signWith(SignatureAlgorithm.HS256, "azerty")
                    .compact();

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (AuthenticationException e) {

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }
}
