package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.model.Skill;
import edu.ban7.demo26cdamns.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillDao extends JpaRepository<Skill, Integer> {
    
}
