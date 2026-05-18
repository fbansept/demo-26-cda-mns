package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.service.AppUserService;
import edu.ban7.demo26cdamns.view.AppUserView;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AppUserService userService;

    @PostMapping("/sign-in")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> signIn(
            @RequestBody @Validated(AppUser.OnCreate.class) AppUser userToInsert) {

        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);

    }
}
