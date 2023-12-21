package com.dailyon.wishcartservice.cart.dto.response;

import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;
import static com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductListResponse.ReadWishCartProductResponse;

import com.dailyon.wishcartservice.cart.entity.Cart;
import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadCartListResponse {
    private List<ReadCartResponse> responses;

    public static ReadCartListResponse create(Cart cart, ReadWishCartProductListResponse responses) {
        return ReadCartListResponse.builder().responses(cart.getCartItems().stream()
                .flatMap(cartItem -> responses.getResponses().stream()
                        .filter(response -> cartItem.getProductId().equals(response.getProductId()) &&
                                cartItem.getProductSizeId().equals(response.getProductSizeId()))
                        .map(response -> ReadCartResponse.create(cartItem, response)))
                .collect(Collectors.toList())).build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadCartResponse {
        private Long productId;
        private Long productSizeId;
        private Integer quantity;
        private String lastMemberCode;
        private String productSizeName;
        private String productName;
        private String brandName;
        private String gender;

        public static ReadCartResponse create(CartItem cartItem, ReadWishCartProductResponse response) {
            return ReadCartResponse.builder()
                    .productId(cartItem.getProductId())
                    .productSizeId(cartItem.getProductSizeId())
                    .quantity(cartItem.getQuantity())
                    .lastMemberCode(cartItem.getLastMemberCode())
                    .productSizeName(response.getProductSizeName())
                    .productName(response.getProductName())
                    .brandName(response.getBrandName())
                    .gender(response.getGender())
                    .build();
        }
    }
}
