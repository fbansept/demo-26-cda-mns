package edu.ban7.demo26cdamns.controller;

import edu.ban7.demo26cdamns.dao.RoleDao;
import edu.ban7.demo26cdamns.model.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    
    protected final RoleDao roleDao;

//    @Autowired
//    public RoleController(RoleDao roleDao) {
//        this.roleDao = roleDao;
//    }

    @GetMapping("/list")
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {

        Optional<Role> optionalRole = roleDao.findById(id);

        if(optionalRole.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //return ResponseEntity.ok(optionalRole.get());
        return new ResponseEntity<>(optionalRole.get(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Role> create(
            @RequestBody
            @Valid
            Role roleToInsert) {

        roleToInsert.setId(null);

        roleDao.save(roleToInsert);

        return new ResponseEntity<>(roleToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        Optional<Role> optionalRole = roleDao.findById(id);

        if(optionalRole.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody
            @Valid
            Role roleToUpdate) {

        Optional<Role> optionalRole = roleDao.findById(id);

        if(optionalRole.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on écrase l'id du json par celui en paramètre
        roleToUpdate.setId(id);

        roleDao.save(roleToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
