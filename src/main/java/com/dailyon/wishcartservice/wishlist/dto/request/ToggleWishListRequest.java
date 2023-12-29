package com.dailyon.wishcartservice.wishlist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToggleWishListRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Long productSizeId;
}
