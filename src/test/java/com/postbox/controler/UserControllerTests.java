package com.postbox.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postbox.controler.UserController;
import com.postbox.controler.dto.UserDto;
import com.postbox.controler.dto.param.CreateUserParam;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Commit // rollback after each unit test
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTests {
    private static String SERVICE_PATH = "/users";

    @Autowired
    private UserController userController;

    @Autowired
    private UserNoSqlRepository userNoSqlRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Before
    public void before() {
        userNoSqlRepository.deleteAll();
    }

    @Test
    public void testGetByUsernameWhenExists() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser();
        userNoSqlRepository.save(userDummyInDb);

        MvcResult response = this.mockMvc.perform(get(SERVICE_PATH+"/"+userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDto userDtoResponse = objectMapper.readValue(response.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userDummyInDb.getUsername(), userDtoResponse.getUsername());
        assertEquals(userDummyInDb.getEmail(), userDtoResponse.getEmail());
        assertEquals(userDummyInDb.getPathIdentifier(), userDtoResponse.getPathIdentifier());
    }

    @Test
    public void testGetByUsernameWhendoesNOTExist() throws Exception {
        User userDummyNotInDb = UserDocumentFactory.generateUser();

        this.mockMvc.perform(get(SERVICE_PATH+"/"+userDummyNotInDb.getUsername()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testCreateUserWithValidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword", "myPlainPassword");

        String userParamString = objectMapper.writeValueAsString(createUserParam);

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername(), userDummy.getPathIdentifier(), userDummy.getEmail()))))
                .andExpect(status().isCreated());

        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertThat(userInDb.isPresent());
        assertThat(userInDb.get().getId()).isNotEmpty();
        assertThat(userInDb.get().getUsername()).isEqualTo(userDummy.getUsername());
        assertThat(userInDb.get().getEmail()).isEqualTo(userDummy.getEmail());
        assertThat(passwordEncoder.matches(userInDb.get().getEncryptedPassword(), "myPlainPassword"));
    }

    @Test
    public void testCreateUserWithInvalidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword_1", "myPlainPassword_2");

        String userParamString = objectMapper.writeValueAsString(createUserParam);

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername(), userDummy.getPathIdentifier(), userDummy.getEmail()))))
                .andExpect(status().isBadRequest());

        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertThat(userInDb.isPresent()).isFalse();
    }
}
