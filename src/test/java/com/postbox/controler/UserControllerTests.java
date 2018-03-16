package com.postbox.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postbox.config.errorhandling.CustomErrorDto;
import com.postbox.controler.dto.UserDto;
import com.postbox.controler.dto.param.CreateUserParam;
import com.postbox.controler.dto.param.UserUpdateParam;
import com.postbox.document.User;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testGetByUsernameWhenDoesNOTExist() throws Exception {
        User userDummyNotInDb = UserDocumentFactory.generateUser();

        MvcResult response = this.mockMvc.perform(get(SERVICE_PATH+"/"+userDummyNotInDb.getUsername()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        CustomErrorDto customErrorDto = objectMapper.readValue(response.getResponse().getContentAsString(), CustomErrorDto.class);

        assertEquals(HttpStatus.NOT_FOUND, customErrorDto.getStatus());
        assertThat(customErrorDto.getMessage(), containsString(userDummyNotInDb.getUsername()));
    }

    @Test
    public void testUpdateExistingUser() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser();
        userNoSqlRepository.save(userDummyInDb);
        UserUpdateParam userUpdateParam = new UserUpdateParam("UserController#update_email1@example.com");
        String userUpdateParamString = objectMapper.writeValueAsString(userUpdateParam);

        this.mockMvc.perform(patch(SERVICE_PATH + "/" + userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON).content(userUpdateParamString))
                .andExpect(status().isOk());

        assertEquals(userNoSqlRepository.findByUsername(userDummyInDb.getUsername()).getEmail(), "UserController#update_email1@example.com");
    }

    @Test
    public void testUpdateNonExistingUser() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser();
        UserUpdateParam userUpdateParam = new UserUpdateParam("UserController#update_email2@example.com");
        String userUpdateParamString = objectMapper.writeValueAsString(userUpdateParam);

        this.mockMvc.perform(patch(SERVICE_PATH + "/" + userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON).content(userUpdateParamString))
                .andExpect(status().isNotFound());

        assertNull(userNoSqlRepository.findByUsername(userDummyInDb.getUsername()));
    }

    @Test
    public void testCreateUserWithValidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword", "myPlainPassword");
        String userParamString = objectMapper.writeValueAsString(createUserParam);

        MvcResult response  = this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
//                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername(), userDummy.getPathIdentifier(), userDummy.getEmail()))))
                .andExpect(status().isCreated())
                .andReturn();

        // check response
        UserDto responseUserDto = objectMapper.readValue(response.getResponse().getContentAsString(), UserDto.class);
        assertEquals(createUserParam.getUsername(), responseUserDto.getUsername());

        // check created DB object
        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertTrue(userInDb.isPresent());
        assertNotNull(userInDb.get().getId());
        assertEquals(userDummy.getUsername(), userInDb.get().getUsername());
        assertEquals(userDummy.getEmail(), userInDb.get().getEmail());
        assertTrue(passwordEncoder.matches("myPlainPassword", userInDb.get().getEncryptedPassword()));
    }

    @Test
    public void testCreateUserWithInvalidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword_1", "myPlainPassword_2");

        String userParamString = objectMapper.writeValueAsString(createUserParam);

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(status().isBadRequest());

        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertFalse(userInDb.isPresent());
    }
}
