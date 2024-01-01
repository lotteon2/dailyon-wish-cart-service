package com.dailyon.wishcartservice.common.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadWishCartProductMapResponse {
    private Map<String, ReadWishCartProductResponse> responses;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadWishCartProductResponse {
        private Long productId;
        private Long productSizeId;
        private Long categoryId;
        private String productSizeName;
        private String brandName;
        private String productName;
        private String gender;
        private Long productQuantity;
        private Integer productPrice;
        private String imgUrl;
    }
}
