package edu.ban7.demo26cdamns.dao;

import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, Integer> {
    List<AppUser> findAllByRole(Role role);

    // SELECT * FROM app_user as u
    // JOIN role as r ON u.role_id = r.id
    // WHERE r.name = 'ADMIN'
    @Query("FROM AppUser u JOIN u.role r WHERE r.name = 'ADMIN'")
    List<AppUser> retourneListeAdmin();


    @Query("FROM AppUser u JOIN u.role r WHERE r.name = :nomRole")
    List<AppUser> retourneListeSelonNomRole(@Param("nomRole") String nomRole);
    
    //--- version à retenir en cas de données  représentant une entité ---
    @Query("FROM AppUser u JOIN u.role r WHERE r = :role")
    List<AppUser> retourneListeSelonRole(@Param("role") Role role);

    @Query("SELECT COUNT(*) FROM AppUser u JOIN u.role r WHERE r.name = 'ADMIN'")
    Integer retourneNombreAdmin();

    @Query("SELECT coalesce(r.name, 'GUEST') , COUNT(*) as count FROM AppUser u LEFT JOIN u.role r GROUP BY r.name")
    Object[][] retourneTableauRepartitionRole();

    //--- version à retenir en cas de données ne représentant pas une entité (via DTO) ---
    @Query( "SELECT new edu.ban7.demo26cdamns.dto.AppUserStat(coalesce(r.name, 'GUEST') , COUNT(*)) " +
            "FROM AppUser u " +
            "LEFT JOIN u.role r " +
            "GROUP BY r.name")
    List<AppUserStat> retourneStatRole();


}
