package com.dailyon.wishcartservice.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCartRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Long productSizeId;
    @NotNull
    private Long quantity;

    private String lastMemberCode;
}
