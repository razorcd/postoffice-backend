package com.postbox.Controller;

import com.postbox.controler.BoxController;
import com.postbox.factory.RequestDouble;
import com.postbox.model.IncomingRequest;
import com.postbox.repository.IncomingRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Commit // rollback after each unit test
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
public class BoxControllerTests {
    //    TODO: cat't add parameterized unit tests to run the test on different request methods ({"get", "post" ... })
    //    TODO: because this expects to change the SpringJUnit4ClassRunner test runner to Parameterized test runner.
    //    TODO: In JUnit 5 use it's new Parameters feature.

    //    TODO: @Value("{}")
    private static String SERVICE_HOST = "http://localhost";
    private static String SERVICE_PATH = "/incoming/";

    @Autowired
    private BoxController boxController;

    @Autowired
    private IncomingRequestRepository incomingRequestRepository;

    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();
    }

    @Test
    public void testRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_HOST+SERVICE_PATH+requestDouble.getUrl());

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());

        Optional<IncomingRequest> incomingRequest = incomingRequestRepository.findAll().stream().findFirst();
        assertThat(incomingRequest.isPresent());
        assertThat(incomingRequest.get().getId()).isEqualTo(1);
        assertThat(incomingRequest.get().getUrl()).isEqualTo(requestDouble.getUrl());
        assertThat(incomingRequest.get().getMethod()).isEqualTo(requestDouble.getMethod());
//        assertThat(incomingRequest.get().getParams()).isEqualTo(requestDouble.getParams());
//        assertThat(incomingRequest.get().getHeaders()).isEqualTo(requestDouble.getHeaders());
//        assertThat(incomingRequest.get().getCookies()).isEqualTo(requestDouble.getCookies());
        assertThat(incomingRequest.get().getBody()).isEqualTo(requestDouble.getBody());
    }
}
