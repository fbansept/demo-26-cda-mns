package edu.ban7.demo26cdamns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.dao.ComponentDao;
import edu.ban7.demo26cdamns.dao.SkillDao;
import edu.ban7.demo26cdamns.dao.TagDao;
import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.Component;
import edu.ban7.demo26cdamns.model.Skill;
import edu.ban7.demo26cdamns.model.Tag;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SkillController {

    protected final SkillDao skillDao;

    @GetMapping("/skill/list/{elementPerPage}/{indexPage}")
    public Page<Skill> getAllPaginated(
            @PathVariable int elementPerPage,
            @PathVariable int indexPage
    ) {
        Pageable pagination = PageRequest.of(indexPage, elementPerPage);
        return skillDao.findAll(pagination);
    }

}
