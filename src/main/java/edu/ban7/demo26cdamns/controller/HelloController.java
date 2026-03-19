package edu.ban7.demo26cdamns.controller;

import edu.ban7.demo26cdamns.dao.AppUserDao;
import edu.ban7.demo26cdamns.model.AppUser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {


        return "<h1>Hello World</h1>";
    }

}
