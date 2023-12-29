package com.dailyon.wishcartservice.controller;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니 등록/수정 성공")
    void upsertCartSuccess() throws Exception {
        // given
        UpsertCartRequest upsertCartRequest = UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(1L)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/cart")
                        .header("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCartRequest))
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("장바구니 등록/수정 실패 - 개수 미포함")
    void upsertCartFail2() throws Exception {
        // given
        UpsertCartRequest upsertCartRequest = UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/cart")
                        .header("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCartRequest))
        );

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("장바구니 삭제 성공")
    void deleteCartSuccess() throws Exception {
        // given
        DeleteCartListRequest request = DeleteCartListRequest.builder()
                .requests(List.of(
                        DeleteCartListRequest.DeleteCartRequest.builder().productSizeId(1L).productId(1L).build(),
                        DeleteCartListRequest.DeleteCartRequest.builder().productSizeId(1L).productId(1L).build())
                ).build();

        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/cart")
                        .header("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
