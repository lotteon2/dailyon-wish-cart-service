package com.dailyon.wishcartservice.common.feign.request;

import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadWishCartProductRequest {
    private Long productId;
    private Long productSizeId;

    public static ReadWishCartProductRequest fromEntity(CartItem cartItem) {
        return ReadWishCartProductRequest.builder()
                .productId(cartItem.getProductId())
                .productSizeId(cartItem.getProductSizeId())
                .build();
    }
}
