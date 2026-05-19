package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.model.Acknowledge;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComponentDao extends JpaRepository<Component, Integer> {

    @Query("FROM Component c")
    List<Component> retourneTout();

    @Query("FROM Component co JOIN co.creator cr WHERE cr == :createur")
    List<Component> retourneToutLesComposantsDuCreateur(@Param("createur") AppUser createur);

    List<Component> findAllByCreator(AppUser creator);

}
