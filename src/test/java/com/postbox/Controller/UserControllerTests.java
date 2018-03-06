package com.postbox.Controller;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Commit // rollback after each unit test
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTests {
    //    TODO: cat't add parameterized unit tests to run the test on different request methods ({"get", "post" ... })
    //    TODO: because this expects to change the SpringJUnit4ClassRunner test runner to Parameterized test runner.
    //    TODO: In JUnit 5 use it's new Parameters feature.

    //    TODO: @Value("{}")
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


    //TODO: add JUnit5 and nest these 2:
    @Test
    public void testCreateUserWithValidPassword() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");
        CreateUserParam createUserParam = new CreateUserParam(userDummy.getUsername(), userDummy.getEmail(),
                "myPlainPassword", "myPlainPassword");

        String userParamString = objectMapper.writeValueAsString(createUserParam);

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername(), userDummy.getEmail()))))
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
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername(), userDummy.getEmail()))))
                .andExpect(status().isBadRequest());

        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertThat(userInDb.isPresent()).isFalse();
    }
}
