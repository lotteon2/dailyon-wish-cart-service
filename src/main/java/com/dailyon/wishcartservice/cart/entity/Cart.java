package com.dailyon.wishcartservice.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cart {
    @MongoId(targetType = FieldType.INT64)
    private Long memberId;

    @Field(targetType = FieldType.ARRAY, write = Field.Write.NON_NULL)
    private List<CartItem> cartItems;

    public static Cart init(Long memberId, Long productId, Long productSizeId, Integer quantity, String lastMemberCode) {
        return Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.init(productId, productSizeId, quantity, lastMemberCode)))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        @Field(targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productId;

        @Field(targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productSizeId;

        @Field(targetType = FieldType.INT32, write = Field.Write.NON_NULL)
        private Integer quantity;

        @Field(targetType = FieldType.STRING, write = Field.Write.ALWAYS)
        private String lastMemberCode;

        @CreatedDate
        @Builder.Default
        @Field(targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Field(targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
        private LocalDateTime updatedAt = LocalDateTime.now();

        public static Cart.CartItem init(Long productId, Long productSizeId, Integer quantity, String lastMemberCode) {
            return Cart.CartItem.builder()
                    .productId(productId)
                    .productSizeId(productSizeId)
                    .quantity(quantity)
                    .lastMemberCode(lastMemberCode)
                    .build();
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public void setLastMemberCode(String lastMemberCode) {
            this.lastMemberCode = lastMemberCode;
        }
    }
}
