package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentDao extends JpaRepository<Component, Integer> {
}
