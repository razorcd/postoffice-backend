package com.postbox.postbox.Controller;

import com.postbox.postbox.controler.BoxController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void testRecordRequest() throws Exception {
        this.mockMvc.perform(get("/any_path").accept(MediaType.ALL))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }
}
