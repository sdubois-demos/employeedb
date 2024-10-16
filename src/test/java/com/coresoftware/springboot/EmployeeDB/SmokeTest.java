package com.coresoftware.springboot.EmployeeDB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.coresoftware.springboot.EmployeeDB.controller.HelloController;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SmokeTest {
        @Autowired
        private HelloController controller;

        @Test
        void contextLoads() throws Exception {
                assertThat(controller).isNotNull();
        }

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnDefaultMessage() throws Exception {
                this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
                                .andExpect(content().string(containsString("Thymeleaf Demo")));
        }
}

