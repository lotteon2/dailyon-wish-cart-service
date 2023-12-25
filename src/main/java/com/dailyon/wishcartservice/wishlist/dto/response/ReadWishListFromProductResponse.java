package com.dailyon.wishcartservice.wishlist.dto.response;

import com.dailyon.wishcartservice.wishlist.document.WishList;
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
public class ReadWishListFromProductResponse {
    private List<ReadWishListResponse> responses;

    public static ReadWishListFromProductResponse fromEntity(List<WishList> wishLists) {
        return ReadWishListFromProductResponse.builder()
                .responses(wishLists.stream()
                        .map(ReadWishListResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadWishListResponse {
        private Long productId;
        private Long productSizeId;

        public static ReadWishListResponse fromEntity(WishList wishList) {
            return ReadWishListResponse.builder()
                    .productId(wishList.getProductId())
                    .productSizeId(wishList.getProductSizeId())
                    .build();
        }
    }
}
