package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, Integer> {
}
