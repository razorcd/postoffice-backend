package com.postbox.Controller;

import com.postbox.controler.BoxController;
import com.postbox.factory.RequestDouble;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@Commit // rollback after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
public class BoxControllerTests {

    //    TODO: @Value("{}")
    private static String SERVICE_PATH = "/incoming/";

    @Autowired
    private BoxController boxController;

    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();

    }

    @Test
    public void testGetRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_PATH+requestDouble.getUrl());
        requestDouble.setMethod("GET");

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
               .andExpect(content().string(""))
               .andExpect(status().isNoContent());
    }

    @Test
    public void testPostRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_PATH+requestDouble.getUrl());
        requestDouble.setMethod("POST");

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPutRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_PATH+requestDouble.getUrl());
        requestDouble.setMethod("PUT");

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPatchRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_PATH+requestDouble.getUrl());
        requestDouble.setMethod("PATCH");

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteRecordRequest() throws Exception {
        RequestDouble requestDouble = new RequestDouble();
        requestDouble.setUrl(SERVICE_PATH+requestDouble.getUrl());
        requestDouble.setMethod("DELETE");

        this.mockMvc.perform(requestDouble.getMockHttpServletRequestBuilder())
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }
}
