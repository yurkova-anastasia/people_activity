package com.ku.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByUsernameTest_shouldReturnUserWitchUsernameAlex() throws Exception {

        //when
        var mvcResult = mockMvc
                            .perform(MockMvcRequestBuilders.get("/users/username/?username=alex"))
                            .andExpect(jsonPath("$.username", is("alex")))
                            .andExpect(jsonPath("$.password", is("86543")))
                            .andExpect(jsonPath("$.authorities", hasItems("USER::WRITE", "USER::READ", "USER::DELETE")))
                            .andExpect(status().isOk())
                            .andReturn();

        //then
        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));
    }

    @Test
    public void findByUsernameTest_shouldReturnNotFondException() throws Exception {
        //when
        var mvcResult = mockMvc
                            .perform(MockMvcRequestBuilders.get("/users/username/?username=larisa"))
                            .andExpect(status().isNotFound())
                            .andReturn();

        //then
        assertThat(mvcResult.getResponse().getContentType(), is("text/plain;charset=UTF-8"));
    }

    @Test
    public void findByUsernameTest_shouldReturnBadRequest() throws Exception {
        //when
        var mvcResult = mockMvc
                            .perform(MockMvcRequestBuilders.get("/users/username/?username="))
                            .andExpect(status().isBadRequest())
                            .andReturn();

        //then
        assertThat(mvcResult.getResponse().getContentType(), is("text/plain;charset=UTF-8"));
    }
}