package com.dailyon.wishcartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WishListControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /*@Test
    @DisplayName("자신의 찜 목록 조회 성공")
    void readWishListSuccess1() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/wish-list")
                        .header("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }*/

    /*@Test
    @DisplayName("남의 찜 목록 조회 성공")
    void readWishListSuccess2() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/wish-list")
                        .header("memberId", 1L)
                        .header("targetId", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}
