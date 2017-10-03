package com.postbox.Controller;

import com.postbox.controler.BoxController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)  // reload spring context after each unit test
@Commit // rollback after each unit test
@RunWith(SpringJUnit4ClassRunner.class)
public class BoxControllerTests {

    @Autowired
    private BoxController boxController;

    private MockMvc mockMvc;

    private String servicePath = "/incoming";

    @Before
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();

//        RequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, URI.create(servicePath+"/any_path"));
    }

    @Test
    public void testGetRecordRequest() throws Exception {
        this.mockMvc.perform(
                    get(servicePath+"/any_path"))
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPostRecordRequest() throws Exception {
        this.mockMvc.perform(
                    post(servicePath+"/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPutRecordRequest() throws Exception {
        this.mockMvc.perform(
                    put(servicePath+"/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPatchRecordRequest() throws Exception {
        this.mockMvc.perform(
                    patch(servicePath+"/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
        Thread.sleep(15000L);

    }

    @Test
    public void testDeleteRecordRequest() throws Exception {
        this.mockMvc.perform(
                    delete(servicePath+"/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(""))
                .andExpect(status().isNoContent());
    }
}
