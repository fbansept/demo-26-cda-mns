package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.ComponentDao;
import edu.ban7.demo26cdamns.model.Component;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ComponentController {
    
    protected ComponentDao componentDao;

    @Autowired
    public ComponentController(ComponentDao componentDao) {
        this.componentDao = componentDao;
    }

    @GetMapping("/component/list")
    @JsonView(ComponentView.class)
    public List<Component> getAll() {
        return componentDao.findAll();
    }

    @GetMapping("/component/{id}")
    @JsonView(ComponentView.class)
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
    public ResponseEntity<Component> create(
            @RequestBody @Valid Component componentToInsert) {

        componentToInsert.setId(null);

        componentDao.save(componentToInsert);

        return new ResponseEntity<>(componentToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/component/{id}")
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
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody
            @Valid
            Component componentToUpdate) {

        Optional<Component> optionalComponent = componentDao.findById(id);

        if(optionalComponent.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on écrase l'id du json par celui en paramètre
        componentToUpdate.setId(id);

        componentDao.save(componentToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
