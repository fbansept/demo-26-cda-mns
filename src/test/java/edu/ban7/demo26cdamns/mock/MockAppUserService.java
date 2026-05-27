package edu.ban7.demo26cdamns.mock;

import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.model.Role;
import edu.ban7.demo26cdamns.service.IAppUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockAppUserService implements IAppUserService {
    @Override
    public List<AppUser> findAll() {
        return List.of();
    }

    @Override
    public List<AppUser> getAllAdminWithStream() {
        return List.of();
    }

    @Override
    public List<AppUser> getAllAdminV2() {
        return List.of();
    }

    @Override
    public List<AppUser> getAllAdminV3() {
        return List.of();
    }

    @Override
    public List<AppUser> getAllAdminV4() {
        return List.of();
    }

    @Override
    public List<AppUser> getAllAdmin() {
        return List.of();
    }

    @Override
    public Object[][] getStatAdmin() {
        return new Object[0][];
    }

    @Override
    public List<AppUserStat> getStatAdminV2() {
        return List.of();
    }

    @Override
    public Optional<AppUser> findById(int id) {
        if(id == 1) {
            Role roleAdmin = new Role(1,"ADMIN");
            AppUser fakeUser = new AppUser(
                    1,
                    "a@a.com",
                    "root",
                    "UserA",
                    roleAdmin, new ArrayList<>(),
                    LocalDateTime.of(2023,12,20,0,0,0),
                    LocalDateTime.now());

            return Optional.of(fakeUser);
        }

        return Optional.empty();
    }

    @Override
    public void insert(AppUser user) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id, AppUser userToUpdate) throws UserNotFoundException {

    }
}
