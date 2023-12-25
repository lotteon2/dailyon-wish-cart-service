package com.dailyon.wishcartservice.common.feign.request;

import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;

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

    public static ReadWishCartProductRequest fromEntity(CartItem cartItem) {
        return ReadWishCartProductRequest.builder()
                .productId(cartItem.getProductId())
                .productSizeId(cartItem.getProductSizeId())
                .build();
    }

    public static ReadWishCartProductRequest fromEntity(WishList wishList) {
        return ReadWishCartProductRequest.builder()
                .productId(wishList.getProductId())
                .productSizeId(wishList.getProductSizeId())
                .build();
    }
}
