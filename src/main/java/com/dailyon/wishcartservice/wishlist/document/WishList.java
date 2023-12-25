package com.dailyon.wishcartservice.wishlist.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
@CompoundIndexes({
        @CompoundIndex(
                name = "wish_list_unique_idx",
                def = "{'memberId' : 1, 'productId' : 1, 'productSizeId' : 1}",
                unique = true
        )
})
public class WishList {
    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    @Indexed(name = "wish_list_member_idx", direction = IndexDirection.ASCENDING)
    @Field(name = "memberId", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
    private Long memberId;

    @Field(name = "productId", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
    private Long productId;

    @Field(name = "productSizeId", targetType = FieldType.INT64, write = Field.Write.NON_NULL)
    private Long productSizeId;

    @CreatedDate
    @Builder.Default
    @Field(name = "createdAt", targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static WishList create(Long memberId, Long productId, Long productSizeId) {
        return WishList.builder()
                .memberId(memberId)
                .productId(productId)
                .productSizeId(productSizeId)
                .build();
    }

    public String toKey() {
        return "pid=" + this.productId + "&sid=" + this.productSizeId;
    }
}
