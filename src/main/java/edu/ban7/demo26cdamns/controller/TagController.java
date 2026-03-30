package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.ComponentDao;
import edu.ban7.demo26cdamns.dao.TagDao;
import edu.ban7.demo26cdamns.model.Component;
import edu.ban7.demo26cdamns.model.Tag;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TagController {

    protected final TagDao tagDao;

    @GetMapping("/tag/list")
    public List<Tag> getAll() {

        List<Tag> tags = tagDao.findAll();
        
        return tags;
    }

}
