package com.dailyon.wishcartservice.cart.dto.request;

import static dailyon.domain.order.kafka.OrderDTO.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartListRequest {
    @Valid
    @NotNull(message = "삭제할 장바구니 목록을 지정해주세요")
    List<DeleteCartRequest> requests;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteCartRequest {
        @NotNull(message = "상품을 입력해주세요")
        private Long productId;
        @NotNull(message = "치수를 입력해주세요")
        private Long productSizeId;

        public static DeleteCartRequest fromDto(ProductInfo productInfo) {
            return DeleteCartRequest.builder()
                    .productId(productInfo.getProductId())
                    .productSizeId(productInfo.getSizeId())
                    .build();
        }
    }
}
