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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ActiveProfiles("test")
public class CartRepositoryTests {
    @Autowired
    CartRepository cartRepository;

    @Test
    void test() {
        Cart cart = cartRepository.save(Cart.builder().memberId(1L).cartItems(new ArrayList<>()).build());

        System.out.println(cart);
    }

    @Test
    @DisplayName("장바구니에 등록하지 않았던 상품의 경우 Cart가 create된다")
    void createWhenUpsertTest() {
        // given
        Long memberId = 1L;
        UpsertCartRequest request = UpsertCartRequest.builder()
                .productId(1L)
                .productSizeId(1L)
                .quantity(1L)
                .lastMemberCode("TEST")
                .build();
        // when
        cartRepository.upsert(memberId, request);

        // then
        Optional<Cart> cart = cartRepository.findById(memberId);
//        Assertions.assertEquals(cart.size(), 1L);
    }

    @Test
    @DisplayName("장바구니에 등록했던 상품의 경우 Cart가 update된다")
    void updateWhenUpsertTest() {

    }
}
