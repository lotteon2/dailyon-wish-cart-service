package com.dailyon.wishcartservice.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Field(name = "cartItems", targetType = FieldType.ARRAY, write = Field.Write.NON_NULL)
    private List<CartItem> cartItems;

    public static Cart init(Long memberId, Long productId, Long productSizeId, Long quantity, String lastMemberCode) {
        return Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.create(productId, productSizeId, quantity, lastMemberCode)))
                .build();
    }

    public static Cart init(Long memberId, Long productId, Long productSizeId, Long quantity) {
        return Cart.builder()
                .memberId(memberId)
                .cartItems(List.of(Cart.CartItem.create(productId, productSizeId, quantity)))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        @Field(name = "productId", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productId;

        @Field(name = "productSizeId", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productSizeId;

        @Field(name = "quantity", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long quantity;

        @Field(name = "lastMemberCode", targetType = FieldType.STRING, write = Field.Write.ALWAYS)
        private String lastMemberCode;

        @CreatedDate
        @Builder.Default
        @Field(name = "createdAt", targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Field(name = "updateAt", targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
        private LocalDateTime updatedAt = LocalDateTime.now();

        public static Cart.CartItem create(Long productId, Long productSizeId, Long quantity, String lastMemberCode) {
            return CartItem.builder()
                    .productId(productId)
                    .productSizeId(productSizeId)
                    .quantity(quantity)
                    .lastMemberCode(lastMemberCode)
                    .build();
        }
        public static Cart.CartItem create(Long productId, Long productSizeId, Long quantity) {
            return CartItem.builder()
                    .productId(productId)
                    .productSizeId(productSizeId)
                    .quantity(quantity)
                    .build();
        }

        public String toKey() {
            return "pid=" + this.productId + "&sid=" + this.productSizeId;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public void setLastMemberCode(String lastMemberCode) {
            this.lastMemberCode = lastMemberCode;
        }
    }
}
