package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.service.AppUserService;
import edu.ban7.demo26cdamns.view.AppUserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "AppUser", description = "API pour manipuler les utilisateurs")
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {
    
    protected final AppUserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/list")
    @JsonView(AppUserView.class)
    public List<AppUser> getAll() {
        return userService.findAll();
    }

    @GetMapping("/user/list-admin")
    @JsonView(AppUserView.class)
    public List<AppUser> getAllAdmin() {
        return userService.getAllAdmin();
    }


    @GetMapping("/user/stat-admin")
    public List<AppUserStat> getStatAdmin() {
        return userService.getStatAdminV2();
    }

    @GetMapping("/user/{id}")
    @JsonView(AppUserView.class)
    @Operation(
            summary = "Fetch user by id",
            description = "fetch the user with id in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "id user not found")
    })
    public ResponseEntity<AppUser> get(@PathVariable int id) {

        Optional<AppUser> optionalUser = userService.findById(id);

        if(optionalUser.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //return ResponseEntity.ok(optionalUser.get());
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping("/user")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> create(
            @RequestBody
            @Validated(AppUser.OnCreate.class)
            AppUser userToInsert) {

        userToInsert.setPassword(passwordEncoder.encode(userToInsert.getPassword()));
        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        Optional<AppUser> optionalUser = userService.findById(id);

        if(optionalUser.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody
            @Validated(AppUser.OnUpdate.class)
            AppUser userToUpdate) {

        try {
            userService.update(id, userToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (AppUserService.UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
