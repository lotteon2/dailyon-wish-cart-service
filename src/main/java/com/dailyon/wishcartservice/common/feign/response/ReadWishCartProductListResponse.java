package com.dailyon.wishcartservice.common.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadWishCartProductListResponse {
    private List<ReadWishCartProductResponse> responses;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadWishCartProductResponse {
        private Long productId;
        private Long productSizeId;
        private String productSizeName;
        private String brandName;
        private String productName;
        private String gender;
    }
}
