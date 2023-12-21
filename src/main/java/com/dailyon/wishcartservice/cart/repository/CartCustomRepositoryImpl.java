package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;

import java.util.ArrayList;
import java.util.List;
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

    public void delete(Long memberId, List<DeleteCartRequest> requests) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Cart.class);

        for (DeleteCartRequest request : requests) {
            Query query = Query.query(Criteria.where("memberId").is(memberId)
                    .andOperator(Criteria.where("cartItems").elemMatch(
                            Criteria.where("productId").is(request.getProductId())
                                    .and("productSizeId").is(request.getProductSizeId())
                    )));
            Update update = new Update().pull("cartItems",
                    Query.query(Criteria.where("productId").is(request.getProductId())
                            .and("productSizeId").is(request.getProductSizeId())
                    ));
            bulkOps.updateOne(query, update);
        }

        bulkOps.execute();
    }
}
