package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartCustomRepositoryImpl implements CartCustomRepository {
    private final MongoTemplate mongoTemplate;

    public void upsert(Long memberId, UpsertCartRequest upsertCartRequest) {
        Query query = Query.query(Criteria.where("memberId").is(memberId)
                .and("cartItems.productId").is(upsertCartRequest.getProductId())
                .and("cartItems.productSizeId").is(upsertCartRequest.getProductSizeId()));
        Update update = new Update().set("cartItems.$.quantity", upsertCartRequest.getQuantity());
        Optional.ofNullable(upsertCartRequest.getLastMemberCode())
                .ifPresent(code -> update.set("cartItems.$.lastMemberCode", code));
        mongoTemplate.upsert(query, update, Cart.class);
    }
}
