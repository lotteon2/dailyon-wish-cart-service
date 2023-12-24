package com.dailyon.wishcartservice.service;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import com.dailyon.wishcartservice.cart.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CartServiceTests {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    @BeforeEach
    void beforeEach() {
        cartRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니에 처음 등록하는 회원의 경우 Cart가 create된다")
    void createNewDocumentTest() {
        // given
        Long memberId = 1L;

        // when
        cartService.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(1L)
                .lastMemberCode("TEST")
                .build());

        // then
        Cart cart = cartRepository.findById(memberId).orElseThrow();
        Assertions.assertEquals(cart.getCartItems().size(), 1L);
        Assertions.assertEquals(cart.getCartItems().get(0).getProductId(), 1L);
        Assertions.assertEquals(cart.getCartItems().get(0).getProductSizeId(), 1L);
        Assertions.assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
        Assertions.assertEquals(cart.getCartItems().get(0).getLastMemberCode(), "TEST");
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 경우 cartItems에 insert된다")
    void createNewCartItemTest() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).build()))
                .build());

        // when
        cartService.upsert(memberId, UpsertCartRequest.builder()
                .productId(2L)
                .productSizeId(2L)
                .quantity(20L)
                .lastMemberCode("TEST")
                .build());

        // then
        Cart cart = cartRepository.findById(memberId).orElseThrow();
        Assertions.assertEquals(cart.getCartItems().size(), 2L);
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다 - 개수 추가")
    void updateWhenUpsertTest1() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).build()))
                .build());

        // when
        cartService.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(20L)
                .build());

        // then
        List<Cart.CartItem> cartItems = cartRepository.findById(memberId).orElseThrow().getCartItems();
        Assertions.assertEquals(cartItems.size(), 1);
        Assertions.assertNull(cartItems.get(0).getLastMemberCode());
        Assertions.assertEquals(cartItems.get(0).getQuantity(), 30);
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다 - 개수와 마지막 추천인 수정")
    void updateWhenUpsertTest2() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).lastMemberCode("BEFORE").build()))
                .build());

        // when
        cartService.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(20L)
                .lastMemberCode("AFTER")
                .build());

        // then
        List<Cart.CartItem> cartItems = cartRepository.findById(memberId).orElseThrow().getCartItems();
        Assertions.assertEquals(cartItems.size(), 1);
        Assertions.assertEquals(cartItems.get(0).getQuantity(), 30);
        Assertions.assertEquals(cartItems.get(0).getLastMemberCode(), "AFTER");
    }
}
