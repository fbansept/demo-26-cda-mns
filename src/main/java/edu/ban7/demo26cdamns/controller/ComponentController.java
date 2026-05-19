package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.ComponentDao;
import edu.ban7.demo26cdamns.model.*;
import edu.ban7.demo26cdamns.security.AppUserDetails;
import edu.ban7.demo26cdamns.security.IsAdmin;
import edu.ban7.demo26cdamns.security.IsSupplier;
import edu.ban7.demo26cdamns.security.IsUser;
import edu.ban7.demo26cdamns.view.AcknowledgeView;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ComponentController {
    
    protected ComponentDao componentDao;

    @Autowired
    public ComponentController(ComponentDao componentDao) {
        this.componentDao = componentDao;
    }

    @GetMapping("/component/list")
    @JsonView(ComponentView.class)
    @IsUser
    public List<Component> getAll() {
        return componentDao.findAll();
    }

    @GetMapping("/component/list-v2")
    @JsonView(ComponentView.class)
    @IsUser
    public List<Component> getAllV2() {

        List<Component> list = componentDao.retourneTout();

        return list;
    }

    @GetMapping("/component/list-by-creator")
    @JsonView(ComponentView.class)
    @IsSupplier
    public List<Component> listByCreator(@AuthenticationPrincipal AppUserDetails userDetails) {
        return componentDao.findAllByCreator(userDetails.getUser());
    }

    @GetMapping("/component/{id}")
    @JsonView(ComponentView.class)
    @IsUser
    public ResponseEntity<Component> get(@PathVariable int id) {

        Optional<Component> optionalComponent = componentDao.findById(id);

        if(optionalComponent.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //return ResponseEntity.ok(optionalComponent.get());
        return new ResponseEntity<>(optionalComponent.get(), HttpStatus.OK);

    }

    @PostMapping("/component")
    @JsonView(ComponentView.class)
    @IsSupplier
    public ResponseEntity<Component> create(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody @Valid Component componentToInsert) {

        componentToInsert.setId(null);
        componentToInsert.setCreator(userDetails.getUser());

        componentDao.save(componentToInsert);

        return new ResponseEntity<>(componentToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/component/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {

        Optional<Component> optionalComponent = componentDao.findById(id);

        if(optionalComponent.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        componentDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/component/{id}")
    @IsSupplier
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @PathVariable int id,
            @RequestBody
            @Valid
            Component componentToUpdate) {

        Optional<Component> optionalComponent = componentDao.findById(id);

        if(optionalComponent.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        //on verifie si l'utilisateur est admin ou si il est le propriétaire de la resource
        //sinon on envoie une erreur 403
        if(!userDetails.getUser().getRole().getName().equals("ADMIN")
                && !optionalComponent.get().getCreator().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //on écrase l'id du json par celui en paramètre
        componentToUpdate.setId(id);

        //on écrase le créateur envoyé dans le JSON dans le cas où un simple supplier l'aurait modifié
        //mais on permet aux admin de modifier le créateur du composant
        if(!userDetails.getUser().getRole().getName().equals("ADMIN")) {
            componentToUpdate.setCreator(userDetails.getUser());
        }

        componentDao.save(componentToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
