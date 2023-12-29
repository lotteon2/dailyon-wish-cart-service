package com.dailyon.wishcartservice.cart.dto.response;

import static com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse.ReadWishCartProductResponse;

import com.dailyon.wishcartservice.cart.document.Cart;
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

    public static ReadCartPageResponse create(Page<Cart> carts, ReadWishCartProductMapResponse response) {
        return ReadCartPageResponse.builder()
                .totalPages(carts.getTotalPages())
                .totalElements(carts.getTotalElements())
                .responses(carts.stream().map(
                        cart -> ReadCartResponse.create(cart, response.getResponses().get(cart.toKey()))
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
        private Long quantity;
        private String lastMemberCode;
        private String productSizeName;
        private String productName;
        private String brandName;
        private String gender;
        private Long productQuantity;
        private Integer productPrice;
        private String imgUrl;

        public static ReadCartResponse create(Cart cart, ReadWishCartProductResponse response) {
            return ReadCartResponse.builder()
                    .productId(cart.getProductId())
                    .productSizeId(cart.getProductSizeId())
                    .quantity(cart.getQuantity())
                    .lastMemberCode(cart.getLastMemberCode())
                    .productSizeName(response.getProductSizeName())
                    .productName(response.getProductName())
                    .brandName(response.getBrandName())
                    .gender(response.getGender())
                    .productQuantity(response.getProductQuantity())
                    .productPrice(response.getProductPrice())
                    .imgUrl(response.getImgUrl())
                    .build();
        }
    }
}
