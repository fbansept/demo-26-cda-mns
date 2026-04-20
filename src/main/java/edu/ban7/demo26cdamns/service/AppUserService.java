package edu.ban7.demo26cdamns.service;

import edu.ban7.demo26cdamns.dao.AppUserDao;
import edu.ban7.demo26cdamns.dto.AppUserStat;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    
    public static class UserNotFoundException extends Exception {}

    protected final AppUserDao appUserDao;

    public List<AppUser> findAll() {
        return appUserDao.findAll();
    }

    public List<AppUser> getAllAdminWithStream() {

        return appUserDao.findAll().stream()
                .filter(u -> u.getRole().getName().equals("ADMIN"))
                .toList();
    }

    public List<AppUser> getAllAdminV2() {
        return appUserDao.findAllByRole(new Role(1, "ADMIN"));
    }

    public List<AppUser> getAllAdminV3() {
        return appUserDao.retourneListeAdmin();
    }

    public List<AppUser> getAllAdminV4() {
        return appUserDao.retourneListeSelonNomRole("ADMIN");
    }

    public List<AppUser> getAllAdmin() {
        return appUserDao.retourneListeSelonRole(new Role(1, "ADMIN"));
    }

    public Object[][] getStatAdmin() {
        return appUserDao.retourneTableauRepartitionRole();
    }

    public List<AppUserStat> getStatAdminV2() {
        return appUserDao.retourneStatRole();
    }

    public Optional<AppUser> findById(int id) {

        return appUserDao.findById(id);

    }

    public void insert(AppUser user) {
        user.setId(null);
        appUserDao.save(user);
    }

    public void delete(int id) {
        appUserDao.deleteById(id);
    }

    public void update(int id, AppUser userToUpdate) throws UserNotFoundException {
        
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        if(optionalAppUser.isEmpty()) {
           throw new UserNotFoundException();
        }

//        AppUser existingUser = findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // On protège les champs qui ne doivent pas être modifiés
        userToUpdate.setId(id);
        userToUpdate.setEmail(optionalAppUser.get().getEmail());
        userToUpdate.setPassword(optionalAppUser.get().getPassword());
        userToUpdate.setRole(optionalAppUser.get().getRole());

        appUserDao.save(userToUpdate);
    }
}
