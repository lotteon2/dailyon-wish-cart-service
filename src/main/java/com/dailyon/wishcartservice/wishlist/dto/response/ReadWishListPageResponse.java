package com.dailyon.wishcartservice.wishlist.dto.response;

import static com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse.ReadWishCartProductResponse;

import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse;
import com.dailyon.wishcartservice.wishlist.document.WishList;
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
public class ReadWishListPageResponse {
    private boolean isMine;
    private Long totalElements;
    private Integer totalPages;
    private List<ReadWishListResponse> responses;

    public static ReadWishListPageResponse create(boolean isMine, Page<WishList> wishLists, ReadWishCartProductMapResponse response) {
        return ReadWishListPageResponse.builder()
                .isMine(isMine)
                .totalElements(wishLists.getTotalElements())
                .totalPages(wishLists.getTotalPages())
                .responses(wishLists.stream().map(
                        wishList -> ReadWishListResponse.create(wishList, response.getResponses().get(wishList.toKey()))
                ).collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadWishListResponse {
        private Long productId;
        private Long productSizeId;
        private String productName;
        private String productSizeName;
        private String brandName;
        private String gender;
        private String imgUrl;
        private Integer productPrice;

        public static ReadWishListResponse create(WishList wishList, ReadWishCartProductResponse response) {
            return ReadWishListResponse.builder()
                    .productId(wishList.getProductId())
                    .productSizeId(wishList.getProductSizeId())
                    .productName(response.getProductName())
                    .productSizeName(response.getProductSizeName())
                    .brandName(response.getBrandName())
                    .productPrice(response.getProductPrice())
                    .gender(response.getGender())
                    .imgUrl(response.getImgUrl())
                    .build();
        }
    }
}
