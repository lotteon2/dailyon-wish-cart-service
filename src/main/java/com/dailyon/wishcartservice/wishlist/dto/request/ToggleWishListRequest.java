package com.dailyon.wishcartservice.wishlist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToggleWishListRequest {
    private Long productId;
    private Long productSizeId;
}
