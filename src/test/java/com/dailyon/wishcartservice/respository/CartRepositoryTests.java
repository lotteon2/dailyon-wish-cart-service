package com.dailyon.wishcartservice.respository;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @DisplayName("document의 원소 중 productId와 productSizeId가 일치하는 값들만 삭제된다")
    void deleteTest() {
        // given
        cartRepository.save(Cart.init(1L, 1L, 1L, 10, ""));
        cartRepository.save(Cart.init(2L, 1L, 1L, 10, ""));

        Cart cart1 = cartRepository.findById(1L).orElseThrow();
        cart1.getCartItems().add(Cart.CartItem.init(1L, 2L, 20, ""));
        cart1.getCartItems().add(Cart.CartItem.init(2L, 2L, 20, ""));
        cartRepository.save(cart1);

        Cart cart2 = cartRepository.findById(2L).orElseThrow();
        cart2.getCartItems().add(Cart.CartItem.init(1L, 2L, 20, ""));
        cart2.getCartItems().add(Cart.CartItem.init(2L, 2L, 20, ""));
        cartRepository.save(cart2);

        // when
        cartRepository.delete(cart1.getMemberId(),
                List.of(DeleteCartRequest.builder().productId(1L).productSizeId(1L).build(),
                        DeleteCartRequest.builder().productId(1L).productSizeId(2L).build())
        );

        // then
        Cart deletedCart1 = cartRepository.findById(cart1.getMemberId()).orElseThrow();
        Assertions.assertEquals(deletedCart1.getCartItems().size(), 1);
        Assertions.assertEquals(deletedCart1.getCartItems().get(0).getProductId(), 2L);
        Assertions.assertEquals(deletedCart1.getCartItems().get(0).getProductSizeId(), 2L);

        Cart deletedCart2 = cartRepository.findById(cart2.getMemberId()).orElseThrow();
        Assertions.assertEquals(deletedCart2.getCartItems().size(), 3);
    }

    @Test
    @DisplayName("장바구니 페이지네이션 조회")
    void readPagesTest() {
        cartRepository.save(Cart.builder()
                .memberId(1L)
                .cartItems(
                        List.of(Cart.CartItem.init(1L, 1L, 1, ""),
                                Cart.CartItem.init(1L, 1L, 1, ""),
                                Cart.CartItem.init(1L, 1L, 1, ""))
                )
                .build());

        Page<Cart.CartItem> cartItems = cartRepository.readPages(1L, Pageable.ofSize(5));

        Assertions.assertEquals(cartItems.getContent().size(), 3);
        Assertions.assertEquals(cartItems.getTotalElements(), 3);
        Assertions.assertEquals(cartItems.getTotalPages(), 1);
    }
}
