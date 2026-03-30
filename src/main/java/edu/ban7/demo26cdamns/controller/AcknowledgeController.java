package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.AcknowledgeDao;
import edu.ban7.demo26cdamns.model.Acknowledge;
import edu.ban7.demo26cdamns.view.AcknowledgeView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AcknowledgeController {
    
    protected AcknowledgeDao acknowledgeDao;

    @Autowired
    public AcknowledgeController(AcknowledgeDao acknowledgeDao) {
        this.acknowledgeDao = acknowledgeDao;
    }

    @GetMapping("/acknowledge/list")
    @JsonView(AcknowledgeView.class)
    public List<Acknowledge> getAll() {
        return acknowledgeDao.findAll();
    }



    @GetMapping("/acknowledge/{idUser}/{idSkill}")
    @JsonView(AcknowledgeView.Extra.class)
    public ResponseEntity<Acknowledge> get(
            @PathVariable int idUser,
            @PathVariable int idSkill) {

        Optional<Acknowledge> optionalAcknowledge = acknowledgeDao.findById(new Acknowledge.Key(idUser, idSkill));

        if(optionalAcknowledge.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //return ResponseEntity.ok(optionalAcknowledge.get());
        return new ResponseEntity<>(optionalAcknowledge.get(), HttpStatus.OK);

    }

    @PostMapping("/acknowledge")
    @JsonView(AcknowledgeView.class)
    public ResponseEntity<Acknowledge> create(
            @RequestBody
            @Valid Acknowledge acknowledgeToInsert) {

        // Initialiser la clé composite avant le save
        Acknowledge.Key key = new Acknowledge.Key(
                acknowledgeToInsert.getUser().getId(),
                acknowledgeToInsert.getSkill().getId()
        );

        Optional<Acknowledge> optionalAcknowledge = acknowledgeDao.findById(key);

        //verification si l'association existe déjà
        if(optionalAcknowledge.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        acknowledgeToInsert.setId(key);

        acknowledgeDao.save(acknowledgeToInsert);

        return new ResponseEntity<>(acknowledgeToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/acknowledge/{idUser}/{idSkill}")
    public ResponseEntity<Void> delete(
            @PathVariable int idUser,
            @PathVariable int idSkill) {

        Acknowledge.Key key = new Acknowledge.Key(idUser, idSkill);

        Optional<Acknowledge> optionalAcknowledge = acknowledgeDao.findById(key);

        if(optionalAcknowledge.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        acknowledgeDao.deleteById(key);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * Exemple
     * http://localhost:8080/acknowledge/2/1
     * 
     * 
     * @param idUser
     * @param idSkill
     * @param acknowledgeToUpdate
     * @return
     */
    @PutMapping("/acknowledge/{idUser}/{idSkill}")
    public ResponseEntity<Void> update(
            @PathVariable int idUser,
            @PathVariable int idSkill,
            @RequestBody
            @Valid
            Acknowledge acknowledgeToUpdate) {

        Acknowledge.Key key = new Acknowledge.Key(idUser, idSkill);

        Optional<Acknowledge> optionalAcknowledge =
                acknowledgeDao.findById(key);

        if(optionalAcknowledge.isEmpty()) {
            //ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on écrase l'id du json par celui en paramètre
        acknowledgeToUpdate.setId(key);

        acknowledgeDao.save(acknowledgeToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
