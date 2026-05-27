package edu.ban7.demo26cdamns.service;

import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface IAppUserService {
    List<AppUser> findAll();

    List<AppUser> getAllAdminWithStream();

    List<AppUser> getAllAdminV2();

    List<AppUser> getAllAdminV3();

    List<AppUser> getAllAdminV4();

    List<AppUser> getAllAdmin();

    Object[][] getStatAdmin();

    List<AppUserStat> getStatAdminV2();

    Optional<AppUser> findById(int id);

    void insert(AppUser user);

    void delete(int id);

    void update(int id, AppUser userToUpdate) throws UserNotFoundException;

    public static class UserNotFoundException extends Exception {
    }
}
