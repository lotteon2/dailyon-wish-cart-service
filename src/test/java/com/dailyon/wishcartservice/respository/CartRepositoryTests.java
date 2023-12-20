package com.dailyon.wishcartservice.respository;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@ActiveProfiles("test")
public class CartRepositoryTests {
    @Autowired
    CartRepository cartRepository;

    @Test
    @DisplayName("장바구니에 등록하지 않았던 상품의 경우 Cart가 create된다")
    void createWhenUpsertTest() {
        // given
        Long memberId = 1L;
        // when
        cartRepository.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(1L)
                .lastMemberCode("TEST")
                .build());

        // then
        Optional<Cart> cart = cartRepository.findById(memberId);
//        Assertions.assertEquals(cart.size(), 1L);
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다 - 재고만 수정")
    void updateWhenUpsertTest1() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).build()))
                .build());

        // when
        cartRepository.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(20L)
                .build());

        // then
        List<Cart.CartItem> cartItems = cartRepository.findById(memberId).orElseThrow().getCartItems();
        Assertions.assertEquals(cartItems.size(), 1);
        Assertions.assertEquals(cartItems.get(0).getQuantity(), 20L);
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다 - 재고와 마지막 추천인 수정")
    void updateWhenUpsertTest2() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).lastMemberCode("BEFORE").build()))
                .build());

        // when
        cartRepository.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(20L)
                .lastMemberCode("AFTER")
                .build());

        // then
        List<Cart.CartItem> cartItems = cartRepository.findById(memberId).orElseThrow().getCartItems();
        Assertions.assertEquals(cartItems.size(), 1);
        Assertions.assertEquals(cartItems.get(0).getQuantity(), 20L);
        Assertions.assertEquals(cartItems.get(0).getLastMemberCode(), "AFTER");
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다 - 마지막 추천인 새로 등록")
    void updateWhenUpsertTest3() {
        // given
        Long memberId = 1L;
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.builder().productId(1L).productSizeId(1L).quantity(10L).build()))
                .build());

        // when
        cartRepository.upsert(memberId, UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(10L)
                .lastMemberCode("TEST")
                .build());

        // then
        List<Cart.CartItem> cartItems = cartRepository.findById(memberId).orElseThrow().getCartItems();
        Assertions.assertEquals(cartItems.size(), 1);
        Assertions.assertEquals(cartItems.get(0).getLastMemberCode(), "TEST");
    }
}
