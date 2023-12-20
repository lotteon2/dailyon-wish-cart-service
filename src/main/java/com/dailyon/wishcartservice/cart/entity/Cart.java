package com.dailyon.wishcartservice.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cart {
    @MongoId
    private Long id;

    @Field(targetType = FieldType.INT64)
    private Long memberId;

    @Field(targetType = FieldType.ARRAY, write = Field.Write.NON_NULL)
    private List<CartItem> cartItems;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
//    @Document(value = "cartItem")
    @CompoundIndex(def = "{'memberId': 1, 'productId': 1, 'productSizeId': 1}")
    public static class CartItem {
        @Field(targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productId;

        @Field(targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long productSizeId;

        @Field(targetType = FieldType.INT64, write = Field.Write.NON_NULL)
        private Long quantity;

        @Field(targetType = FieldType.STRING, write = Field.Write.ALWAYS)
        private String lastMemberCode;
    }
}
