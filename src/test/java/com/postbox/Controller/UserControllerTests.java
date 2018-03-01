package com.postbox.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postbox.controler.BoxController;
import com.postbox.controler.UserController;
import com.postbox.controler.dto.UserDto;
import com.postbox.controler.dto.param.UserParam;
import com.postbox.document.IncomingRequest;
import com.postbox.document.User;
import com.postbox.factory.ServletRequestDouble;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
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

    @Before
    public void before() {
        userNoSqlRepository.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        User userDummy = UserDocumentFactory.generateUser("myPlainPassword");

        String userParamString = objectMapper.writeValueAsString(new UserParam(userDummy.getUsername(), "myPlainPassword"));

        this.mockMvc.perform(post(SERVICE_PATH).contentType(MediaType.APPLICATION_JSON).content(userParamString))
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(userDummy.getUsername()))))
                .andExpect(status().isCreated());

        Optional<User> userInDb = userNoSqlRepository.findAll().stream().findFirst();
        assertThat(userInDb.isPresent());
        assertThat(userInDb.get().getId()).isNotEmpty();
        assertThat(userInDb.get().getUsername()).isEqualTo(userDummy.getUsername());
        assertThat(userInDb.get().getEncryptedPassword()).isEqualTo(userDummy.getEncryptedPassword());
    }
}
