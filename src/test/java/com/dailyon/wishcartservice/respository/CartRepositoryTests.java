package com.dailyon.wishcartservice.respository;

import com.dailyon.wishcartservice.cart.document.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import com.dailyon.wishcartservice.common.exception.NotExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CartRepositoryTests {
    @Autowired
    CartRepository cartRepository;

    @BeforeEach
    void beforeEach() {
        cartRepository.deleteAll();
    }


    @Test
    @DisplayName("cart 생성")
    void upsertCartTest1() {
        // given, when
        cartRepository.upsert(1L, 1L, 1L, 1L, null);

        // then
        Assertions.assertEquals(1, cartRepository.findAll().size());
    }

    @Test
    @DisplayName("cart update - 개수 추가")
    void upsertCartTest2() {
        // given
        cartRepository.save(Cart.create(1L, 1L, 1L, 1L));

        // when
        cartRepository.upsert(1L, 1L, 1L, 10L, null);

        // then
        Assertions.assertEquals(11L, cartRepository.findAll().get(0).getQuantity());
    }

    @Test
    @DisplayName("cart update - 개수와 마지막 추천인 수정")
    void upsertCartTest4() {
        // given
        cartRepository.save(Cart.create(1L, 1L, 1L, 1L));

        // when
        cartRepository.upsert(1L, 1L, 1L, 10L, "AAAA");

        // then
        Assertions.assertEquals(11L, cartRepository.findAll().get(0).getQuantity());
        Assertions.assertEquals("AAAA", cartRepository.findAll().get(0).getLastMemberCode());
    }

    @Test
    @DisplayName("document의 원소 중 productId와 productSizeId가 일치하는 값들만 삭제된다")
    void deleteCartTest() {
        // given
        cartRepository.save(Cart.create(1L, 1L, 1L, 10L, ""));
        cartRepository.save(Cart.create(1L, 1L, 2L, 20L));
        cartRepository.save(Cart.create(1L, 2L, 2L, 10L));

        // when
        cartRepository.delete(1L,
                List.of(DeleteCartRequest.builder().productId(1L).productSizeId(1L).build(),
                        DeleteCartRequest.builder().productId(1L).productSizeId(2L).build())
        );

        // then
        Assertions.assertEquals(1L, cartRepository.findAll().size());
    }

    @Test
    @DisplayName("장바구니 페이지네이션 조회")
    void readPagesTest() {
        // given
        cartRepository.saveAll(List.of(
                Cart.create(1L, 1L, 1L, 10L),
                Cart.create(1L, 2L, 1L, 10L, "AAA"),
                Cart.create(1L, 1L, 2L, 20L, "BBB")
        ));

        // when
        Page<Cart> carts = cartRepository.readPages(1L, Pageable.ofSize(5));

        // then
        Assertions.assertEquals(carts.getContent().size(), 3);
        Assertions.assertEquals(carts.getTotalElements(), 3);
        Assertions.assertEquals(carts.getTotalPages(), 1);
    }

    @Test
    @DisplayName("장바구니 개수 수정 성공")
    void updateCartSuccess() {
        // given
        cartRepository.save(Cart.create(1L, 1L, 1L, 1L));

        // when
        Cart modified = cartRepository.update(1L, 1L, 1L, 10L);

        // then
        Assertions.assertEquals(10, modified.getQuantity());
    }

    @Test
    @DisplayName("장바구니 개수 수정 실패")
    void updateCartFail() {
        // given, when, then
        Assertions.assertThrows(
                NotExistsException.class,
                () -> cartRepository.update(1L, 1L, 1L, 10L));
    }
}
