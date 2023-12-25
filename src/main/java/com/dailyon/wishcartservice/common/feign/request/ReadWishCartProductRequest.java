package com.dailyon.wishcartservice.common.feign.request;

import com.dailyon.wishcartservice.cart.document.Cart;
import com.dailyon.wishcartservice.wishlist.document.WishList;
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

    public static ReadWishCartProductRequest fromEntity(Cart cart) {
        return ReadWishCartProductRequest.builder()
                .productId(cart.getProductId())
                .productSizeId(cart.getProductSizeId())
                .build();
    }

    public static ReadWishCartProductRequest fromEntity(WishList wishList) {
        return ReadWishCartProductRequest.builder()
                .productId(wishList.getProductId())
                .productSizeId(wishList.getProductSizeId())
                .build();
    }
}
