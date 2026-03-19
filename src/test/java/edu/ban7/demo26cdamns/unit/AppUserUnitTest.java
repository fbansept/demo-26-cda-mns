package edu.ban7.demo26cdamns.unit;

import edu.ban7.demo26cdamns.TestUtils;
import edu.ban7.demo26cdamns.model.AppUser;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppUserUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void createUserWithUpperCasePseudo_shouldPseudoBeLowerCase() {
        AppUser user = new AppUser();
        user.setPseudo("NOUVel UtilisaTeur");

        Assertions.assertEquals("nouvel utilisateur", user.getPseudo());
    }

    @Test
    public void validUserWithBlankEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("");

        //On simule la validation via @Validated(AppUser.OnCreate.class)
        //cad lors de la transformation du JSON en classe Java lors du PostMapping
        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user, AppUser.OnCreate.class),
                "email",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur email n'a pas fonctionné");
    }


    @Test
    public void validUserWithNotWellFormattedEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("a.com");

        //On simule la validation via @Validated(AppUser.OnCreate.class)
        //cad lors de la transformation du JSON en classe Java lors du PostMapping
        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user, AppUser.OnCreate.class),
                "email",
                "Email");

        Assertions.assertTrue(constraintExist,"La contrainte Email sur email n'a pas fonctionné");
    }

    

}
