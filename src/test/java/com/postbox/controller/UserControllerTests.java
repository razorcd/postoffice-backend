package com.postbox.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postbox.config.errorhandling.CustomErrorDto;
import com.postbox.controller.dto.UserDto;
import com.postbox.controller.dto.param.CreateUserParam;
import com.postbox.controller.dto.param.UserUpdateParam;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
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
    private final String SERVICE_PATH = "/users";

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
    @WithMockUser(username = "UserController_GetByUsernameWhenExists_username1", password = "password", roles = "USER")
    public void testGetByUsernameWhenExists() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser("UserController_GetByUsernameWhenExists_username1", "password");
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
    @WithMockUser(username = "UserController_GetByUsernameWhenDoesNOTExist_username2", password = "password", roles = "USER")
    public void testGetByUsernameWhenDoesNOTExist() throws Exception {
        User userDummyNotInDb = UserDocumentFactory.generateUser("UserController_GetByUsernameWhenDoesNOTExist_username2", "password");

        MvcResult response = this.mockMvc.perform(get(SERVICE_PATH+"/"+userDummyNotInDb.getUsername()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        CustomErrorDto customErrorDto = objectMapper.readValue(response.getResponse().getContentAsString(), CustomErrorDto.class);

        assertEquals(HttpStatus.NOT_FOUND, customErrorDto.getStatus());
        assertThat(customErrorDto.getMessage(), containsString(userDummyNotInDb.getUsername()));
    }

    @Test
    @WithMockUser(username = "UserController_GetByUsernameThatIsNotPrincipal_username3", password = "password", roles = "USER")
    public void testGetByUsernameThatIsNotPrincipal() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser("other_username3", "password");
        userNoSqlRepository.save(userDummyInDb);

        this.mockMvc.perform(get(SERVICE_PATH+"/"+userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "UserController_UpdateExistingUser_username1", password = "password", roles = "USER")
    public void testUpdateExistingUser() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser("UserController_UpdateExistingUser_username1", "password");
        userNoSqlRepository.save(userDummyInDb);

        UserUpdateParam userUpdateParam = new UserUpdateParam("UserController#update_email1@example.com");
        String userUpdateParamString = objectMapper.writeValueAsString(userUpdateParam);

        this.mockMvc.perform(patch(SERVICE_PATH + "/" + userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON).content(userUpdateParamString))
                .andExpect(status().isOk());

        assertEquals(userNoSqlRepository.findByUsername(userDummyInDb.getUsername()).getEmail(), "UserController#update_email1@example.com");
    }

    @Test
    @WithMockUser(username = "UserController_UpdateNoExistingUser_username1", password = "password", roles = "USER")
    public void testUpdateNonExistingUser() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("UserController_UpdateNoExistingUser_username1", "password");
        UserUpdateParam userUpdateParam = new UserUpdateParam("UserController#update_email2@example.com");
        String userUpdateParamString = objectMapper.writeValueAsString(userUpdateParam);

        this.mockMvc.perform(patch(SERVICE_PATH + "/" + userDummy.getUsername()).contentType(MediaType.APPLICATION_JSON).content(userUpdateParamString))
                .andExpect(status().isNotFound());

        //assert no other record was created in db
        List<User> anyUsersInDb = userNoSqlRepository.findAll();
        assertTrue(anyUsersInDb.isEmpty());
    }

    @Test
    @WithMockUser(username = "UserController_UpdateUserThatIsNotPrincipal_username1", password = "password", roles = "USER")
    public void testUpdateUserThatIsNotPrincipal() throws Exception {
        User userDummyInDb = UserDocumentFactory.generateUser("other_username1", "password");
        userNoSqlRepository.save(userDummyInDb);

        UserUpdateParam userUpdateParam = new UserUpdateParam("UserController#update_email3@example.com");
        String userUpdateParamString = objectMapper.writeValueAsString(userUpdateParam);

        this.mockMvc.perform(patch(SERVICE_PATH + "/" + userDummyInDb.getUsername()).contentType(MediaType.APPLICATION_JSON).content(userUpdateParamString))
                .andExpect(status().isForbidden());
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
        Optional<User> createdUserInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertTrue(createdUserInDb.isPresent());
        assertNotNull(createdUserInDb.get().getId());
        assertEquals(userDummy.getUsername(), createdUserInDb.get().getUsername());
        assertEquals(userDummy.getEmail(), createdUserInDb.get().getEmail());
        assertTrue(passwordEncoder.matches("myPlainPassword", createdUserInDb.get().getEncryptedPassword()));
    }

    @Test
    public void testCreateUserWithInvalidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword_1", "myPlainPassword_2");

        String userParamString = objectMapper.writeValueAsString(createUserParam);

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(status().isBadRequest());

        List<User> anyUsersInDb = userNoSqlRepository.findAll();
        assertTrue(anyUsersInDb.isEmpty());
    }
}
