package com.dailyon.wishcartservice.cart.dto.response;

import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;
import static com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse.ReadWishCartProductResponse;

import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadCartPageResponse {
    private Long totalElements;
    private Integer totalPages;
    private List<ReadCartResponse> responses;

    public static ReadCartPageResponse create(Page<CartItem> cartItems, ReadWishCartProductMapResponse response) {
        return ReadCartPageResponse.builder()
                .totalPages(cartItems.getTotalPages())
                .totalElements(cartItems.getTotalElements())
                .responses(cartItems.stream().map(
                        cartItem -> ReadCartResponse.create(cartItem, response.getResponses().get(cartItem.toKey()))
                ).collect(Collectors.toList()))
                .build();
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