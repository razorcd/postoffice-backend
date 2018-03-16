package com.postbox.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postbox.controller.dto.IncomingRequestDto;
import com.postbox.document.User;
import com.postbox.factory.IncomingRequestDocumentFactory;
import com.postbox.factory.ServletRequestDouble;
import com.postbox.document.IncomingRequest;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.springframework.data.geo.Distance.between;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@SpringBootTest // share context
@Commit // rollback after each unit test
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class IncomingRequestControllerTests {
    //    TODO: cat't add parameterized unit tests to run the test on different request methods ({"get", "post" ... })
    //    TODO: because this expects to change the SpringJUnit4ClassRunner test runner to Parameterized test runner.
    //    TODO: add JUnit 5 and use it's new Parameters feature.

    @Value("${service.host}")
    private final String SERVICE_HOST = "http://localhost";
    private final String SERVICE_PATH = "/incoming";

    @Autowired
    private IncomingRequestController incomingRequestController;

    @Autowired
    private IncomingRequestNoSqlRepository incomingRequestNoSqlRepository;

    @Autowired
    private UserNoSqlRepository userNoSqlRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void initMockMvc() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(incomingRequestController).build();
//    }

    @Before
    public void before() {
        userNoSqlRepository.deleteAll();
        incomingRequestNoSqlRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "IRController_GetIncomingRequestsForExistingUser_username1", password = "password1")
    public void testGetIncomingRequestsForExistingUser() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("IRController_GetIncomingRequestsForExistingUser_username1", "password1");
        userNoSqlRepository.save(userInDb);
        IncomingRequest incomingRequestInDbForUser = IncomingRequestDocumentFactory.generateIncomingRequest();
        incomingRequestInDbForUser.setUserPathIdentifier(userInDb.getPathIdentifier());
        incomingRequestNoSqlRepository.save(incomingRequestInDbForUser);
        IncomingRequest incomingRequestInDbForOther = IncomingRequestDocumentFactory.generateIncomingRequest();
        incomingRequestNoSqlRepository.save(incomingRequestInDbForOther);

        MvcResult response = mockMvc.perform(get("/users/"+userInDb.getUsername()+"/incomingrequests").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        List<IncomingRequestDto> responseIncomingRequestDtoList = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<IncomingRequestDto>>(){});

        assertEquals(1, responseIncomingRequestDtoList.size());
        assertEquals(incomingRequestInDbForUser.getUrl(), responseIncomingRequestDtoList.get(0).getUrl());
        assertEquals(incomingRequestInDbForUser.getBody(), responseIncomingRequestDtoList.get(0).getBody());
    }

    @Test
    @WithMockUser(username = "IR_GetIRForNonExistingUser_username2", password = "password2")
    public void testGetIncomingRequestsForNonExistingUser() throws Exception {
        IncomingRequest incomingRequestInDbForOther = IncomingRequestDocumentFactory.generateIncomingRequest();
        incomingRequestNoSqlRepository.save(incomingRequestInDbForOther);

        mockMvc.perform(get("/users/"+"IR_GetIRForNonExistingUser_username2"+"/incomingrequests").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateIncomingRequests() throws Exception {
        User userInDb = UserDocumentFactory.generateUser("IRController_CreateIncomingRequests_username3", "password3");
        userNoSqlRepository.save(userInDb);

        LocalDateTime startTime = LocalDateTime.now();
        ServletRequestDouble servletRequestDouble = new ServletRequestDouble();
        servletRequestDouble.setUrl(SERVICE_HOST+SERVICE_PATH+"/"+userInDb.getPathIdentifier()+"/"+servletRequestDouble.getUrl());

        this.mockMvc.perform(servletRequestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());

        Optional<IncomingRequest> createdIncomingRequest = incomingRequestNoSqlRepository.findAll().stream().findFirst();
        assertTrue(createdIncomingRequest.isPresent());
        assertEquals(userInDb.getPathIdentifier(), createdIncomingRequest.get().getUserPathIdentifier());
        assertEquals(servletRequestDouble.getUrl(), createdIncomingRequest.get().getUrl());
        assertEquals(servletRequestDouble.getMethod(), createdIncomingRequest.get().getMethod());
//        assertThat(incomingRequest.get().getParams()).isEqualTo(servletRequestDouble.getParams());
//        assertThat(incomingRequest.get().getHeaders()).isEqualTo(servletRequestDouble.getHeaders());
//        assertThat(incomingRequest.get().getCookies()).isEqualTo(servletRequestDouble.getCookies());
        assertEquals(servletRequestDouble.getBody(), createdIncomingRequest.get().getBody());
        assertThat(createdIncomingRequest.get().getTimestamp(), allOf(greaterThan(startTime), lessThan(LocalDateTime.now())));
    }
}
