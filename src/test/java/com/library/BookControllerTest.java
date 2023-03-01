package com.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/books/test")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Controller works.")));
    }

    @Test
    public void findBookTest() throws Exception {
        this.mockMvc.perform(get("/books/find?findBy=AUTHOR&param=ilya")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"name\":\"book\",\"author\":\"ilya\",\"year\":2004}]")));
    }

    //    @Test
//    public void getBooksTest() throws Exception {
//        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("[]")));
//    }
}
