package com.dailyon.wishcartservice.wishlist.repository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WishListCustomRepositoryImpl implements WishListCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<WishList> readWishListPages(Long memberId, Pageable pageable) {
        Query query = Query.query(Criteria.where("memberId").is(memberId));

        long totalCounts = mongoTemplate.count(query, WishList.class);
        query.with(pageable);

        List<WishList> wishLists = mongoTemplate.find(query, WishList.class);

        return new PageImpl<>(wishLists, pageable, totalCounts);
    }
}
