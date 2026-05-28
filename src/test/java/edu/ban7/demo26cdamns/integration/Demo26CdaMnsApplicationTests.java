package edu.ban7.demo26cdamns.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import edu.ban7.demo26cdamns.dao.ComponentDao;
import edu.ban7.demo26cdamns.model.AppUser;
import edu.ban7.demo26cdamns.model.Component;
import edu.ban7.demo26cdamns.model.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RequiredArgsConstructor
class Demo26CdaMnsApplicationTests {

    private final WebApplicationContext context;

    private ObjectMapper mapper = JsonMapper.builder().build();
    private MockMvc mvc;


    @BeforeEach
    void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void callComponentList_shouldReturnCode200() throws Exception {
        mvc.perform(get("/component/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void callComponentListV2asAnonymous_shouldReturnCode403() throws Exception {
        mvc.perform(get("/component/list-v2"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void callComponentListV2asUser_shouldReturnCode200() throws Exception {
        mvc.perform(get("/component/list-v2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void callDeleteComponentAsUser_shouldReturnCode403() throws Exception {
        mvc.perform(delete("/component/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    //@WithMockUser(roles = {"ADMIN"})// pas possible car l'endpoint utilise un @AuthenticationPrincipal
    @WithUserDetails("a@a.com") //note a@a.com est un admin
    public void callUpdateComponentAsAdmin_shouldReturnCode200() throws Exception {

        AppUser fakeUser = new AppUser(3,"","","", new Role(1,""),
                new ArrayList<>(),null,null);

        Component fakeComponent = new Component(
                null,
                "abc",
                "1111111111",
                "c",
                fakeUser,
                new ArrayList<>(),
                fakeUser);

        String jsonComponent = mapper.writeValueAsString(fakeComponent);

        mvc.perform(put("/component/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComponent))
                .andExpect(status().isNoContent());
    }

    @Test
    //@WithMockUser(roles = {"ADMIN"})// pas possible car l'endpoint utilise un @AuthenticationPrincipal
    @WithUserDetails("c@c.com") //note c@c.com est un supplier mais n'est pas propriétaire du composant modifié
    public void callUpdateComponentAsSupplierButNotOwner_shouldReturnCode403() throws Exception {


        AppUser fakeUser = new AppUser(4,"","","", new Role(1,""),
                new ArrayList<>(),null,null);

        Component fakeComponent = new Component(
                2,
                "abc",
                "0123456789",
                "c",
                fakeUser,
                new ArrayList<>(),
                fakeUser);

        String jsonComponent = mapper.writeValueAsString(fakeComponent);

        mvc.perform(put("/component/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComponent))
                .andExpect(status().isForbidden());
    }

    @Test
    //@WithMockUser(roles = {"ADMIN"})// pas possible car l'endpoint utilise un @AuthenticationPrincipal
    @WithUserDetails("d@d.com") //note c@c.com est un supplier mais n'est pas propriétaire du composant modifié
    public void callUpdateComponentAsSupplierAndOwner_shouldReturnCode204() throws Exception {

        AppUser fakeUser = new AppUser(4,"","","", new Role(1,""),
                new ArrayList<>(),null,null);

        Component fakeComponent = new Component(
                2,
                "abc",
                "0123456789",
                "c",
                fakeUser,
                new ArrayList<>(),
                fakeUser);

        String jsonComponent = mapper.writeValueAsString(fakeComponent);

        mvc.perform(put("/component/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComponent))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callDeleteComponentAsAdmin_shouldReturnCode204() throws Exception {
        mvc.perform(delete("/component/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void callListUser_shouldNotExposePassword() throws Exception {

        mvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.id").exists()) //vérifie si ce n'est pas un json infini
                .andExpect(jsonPath("$.password").doesNotExist());

        mvc.perform(get("/user/list"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }


}
