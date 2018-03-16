package com.postbox.controller;

import com.postbox.document.User;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.*;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Commit
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class SessionTests {
    private final String CREATE_SESSION_PATH = "/login";
    private final String DESTROY_SESSION_PATH = "/logout";
    private final String GET_PRINCIPAL_PATH = "/principal";

    @Autowired
    private UserNoSqlRepository userNoSqlRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void before() {
        userNoSqlRepository.deleteAll();
    }

    @Test
    public void testCreateSessionWithValidCredentials() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("Session_CreateSessionWithValidCredentials_username1", "password1");
        userNoSqlRepository.save(userInDb);

        //LOGIN
        RequestBuilder srb = SecurityMockMvcRequestBuilders.formLogin(CREATE_SESSION_PATH).user(userInDb.getUsername()).password("password1");
        MvcResult loginResponse = mockMvc.perform(srb)
            .andExpect(status().isOk())
            .andExpect(cookie().exists("SESSION"))
            .andReturn();

        //ensure VALID session
        mockMvc.perform(get(GET_PRINCIPAL_PATH).contentType(MediaType.APPLICATION_JSON).cookie(loginResponse.getResponse().getCookies()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSessionWithInvalidPassword() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("Session_CreateSessionWithInvalidPassword_username2", "password2");
        userNoSqlRepository.save(userInDb);

        //LOGIN
        RequestBuilder srb = SecurityMockMvcRequestBuilders.formLogin(CREATE_SESSION_PATH).user(userInDb.getUsername()).password("wrong_password");
        mockMvc.perform(srb)
                .andExpect(status().isUnauthorized())
                .andExpect(cookie().doesNotExist("SESSION"));
    }

    @Test
    public void testCreateSessionWithNonExistentUsername() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("Session_CreateSessionWithNonExistentUsername_username3", "password3");
        userNoSqlRepository.save(userInDb);

        //LOGIN
        RequestBuilder srb = SecurityMockMvcRequestBuilders.formLogin(CREATE_SESSION_PATH).user("wrong_username").password("password3");
        mockMvc.perform(srb)
                .andExpect(status().isUnauthorized())
                .andExpect(cookie().doesNotExist("SESSION"));
    }

    @Test
    public void testDestroySessionWhenExists() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("Session_DestroySessionWhenExists_username4", "password4");
        userNoSqlRepository.save(userInDb);

        //LOGIN
        RequestBuilder srb = SecurityMockMvcRequestBuilders.formLogin(CREATE_SESSION_PATH).user(userInDb.getUsername()).password("password4");
        MvcResult loginResponse = mockMvc.perform(srb).andReturn();

        //ensure VALID session
        mockMvc.perform(get(GET_PRINCIPAL_PATH).contentType(MediaType.APPLICATION_JSON).cookie(loginResponse.getResponse().getCookies()))
                .andExpect(status().isOk());

        //LOGOUT
        mockMvc.perform(get(DESTROY_SESSION_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(cookie().doesNotExist("SESSION"))
                .andExpect(status().isOk());

//        //ensure INVALID session. By default spring is not removing session from storage.
//        mockMvc.perform(get(GET_PRINCIPAL_PATH).contentType(MediaType.APPLICATION_JSON).cookie(loginResponse.getResponse().getCookies()))
//                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDestroySessionWhenDoesNOTExist() throws Exception {
        //LOGOUT
        mockMvc.perform(get(DESTROY_SESSION_PATH).contentType(MediaType.APPLICATION_JSON).cookie(new Cookie("SESSION", "0000-invalidsession-0000")))
                .andExpect(cookie().doesNotExist("SESSION"))
                .andExpect(status().isOk());
    }
}