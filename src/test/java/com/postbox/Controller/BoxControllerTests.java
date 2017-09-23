package com.postbox.Controller;

import com.postbox.controler.BoxController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BoxControllerTests {

    @Autowired
    private BoxController boxController;

    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(boxController)
                .build();
    }

    @Test
    public void testGetRecordRequest() throws Exception {
        this.mockMvc.perform(
                    get("/any_path"))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostRecordRequest() throws Exception {
        this.mockMvc.perform(
                    post("/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutRecordRequest() throws Exception {
        this.mockMvc.perform(
                    put("/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPatchRecordRequest() throws Exception {
        this.mockMvc.perform(
                    patch("/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRecordRequest() throws Exception {
        this.mockMvc.perform(
                    delete("/any_path")
                            .content("Some content string.")
                            .contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }
}
