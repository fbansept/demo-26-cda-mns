package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.AppUserDao;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.view.AppUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppUserController {
    
    protected AppUserDao appUserDao;

    @Autowired
    public AppUserController(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    @GetMapping("/user/list")
    @JsonView(AppUserView.class)
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    @GetMapping("/user/{id}")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> get(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

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

        userToInsert.setId(null);

        appUserDao.save(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody
            @Validated(AppUser.OnUpdate.class)
            AppUser userToUpdate) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on écrase l'id du json par celui en paramètre
        userToUpdate.setId(id);

        //On réaffecte les anciennes valeurs qui ne doivent pas etre changée
        userToUpdate.setEmail(optionalUser.get().getEmail());
        userToUpdate.setPassword(optionalUser.get().getPassword());
        userToUpdate.setRole(optionalUser.get().getRole());

        appUserDao.save(userToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
