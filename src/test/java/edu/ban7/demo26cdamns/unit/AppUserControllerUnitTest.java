package edu.ban7.demo26cdamns.unit;

import edu.ban7.demo26cdamns.controller.AppUserController;
import edu.ban7.demo26cdamns.mock.MockAppUserDao;
import edu.ban7.demo26cdamns.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUserControllerUnitTest {

    @Test
    public void getUserByExistingId_shouldReturnCode200() {

        AppUserController userController = new AppUserController(new MockAppUserDao());
        ResponseEntity<AppUser> reponse = userController.get(1);

        Assertions.assertEquals(HttpStatus.OK, reponse.getStatusCode());

    }
    
    @Test
    public void getUserByNotExistingId_shouldReturnCode404() {
        AppUserController userController = new AppUserController(new MockAppUserDao());
        ResponseEntity<AppUser> reponse = userController.get(2);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, reponse.getStatusCode());
    }

}
