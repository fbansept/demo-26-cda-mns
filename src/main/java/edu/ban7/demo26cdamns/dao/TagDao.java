package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.model.Acknowledge;
import edu.ban7.demo26cdamns.model.Component;
import edu.ban7.demo26cdamns.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {

    @Query("FROM Tag t")
    List<Tag> retourneTout();

}
