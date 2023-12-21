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
    @NotNull(message = "상품을 입력해주세요")
    private Long productId;

    @NotNull(message = "치수를 입력해주세요")
    private Long productSizeId;

    @NotNull(message = "개수를 입력해주세요")
    private Integer quantity;

    private String lastMemberCode;
}
