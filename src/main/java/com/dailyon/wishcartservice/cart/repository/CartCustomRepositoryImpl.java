package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.document.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartCustomRepositoryImpl implements CartCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Cart upsert(Long memberId, Long productId, Long productSizeId, Long quantity, String lastMemberCode) {
        Query query = Query.query(Criteria.where("memberId").is(memberId)
                .and("productId").is(productId)
                .and("productSizeId").is(productSizeId));

        Cart cart = mongoTemplate.findOne(query, Cart.class);
        if(cart == null) { // create, 추천인 코드 분기 처리
            cart = Optional.ofNullable(lastMemberCode)
                    .map(code -> Cart.create(memberId, productId, productSizeId, quantity, lastMemberCode))
                    .orElseGet(() -> Cart.create(memberId, productId, productSizeId, quantity));
        } else { // update, 추천인 코드 분기 처리
            cart.setQuantity(cart.getQuantity() + quantity);
            Optional.ofNullable(lastMemberCode).ifPresent(cart::setLastMemberCode);
        }
        return mongoTemplate.save(cart);
    }

    @Override
    public void delete(Long memberId, List<DeleteCartRequest> requests) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Cart.class);

        requests.forEach(request -> bulkOps.remove(
                Query.query(Criteria.where("memberId").is(memberId)
                        .and("productId").is(request.getProductId())
                        .and("productSizeId").is(request.getProductSizeId()))
        ));

        bulkOps.execute();
    }

    @Override
    public Page<Cart> readPages(Long memberId, Pageable pageable) {
        Query query = Query.query(Criteria.where("memberId").is(memberId));
        long totalCount = mongoTemplate.count(query, Cart.class);

        query.with(pageable);
        List<Cart> carts = mongoTemplate.find(query, Cart.class);

        return new PageImpl<>(carts, pageable, totalCount);
    }
}
