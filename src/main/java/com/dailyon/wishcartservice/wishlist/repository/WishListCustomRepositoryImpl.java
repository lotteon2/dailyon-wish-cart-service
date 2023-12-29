package com.dailyon.wishcartservice.wishlist.repository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import com.dailyon.wishcartservice.wishlist.dto.request.ToggleWishListRequest;
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

    public WishList toggleWishList(Long memberId, Long productId, Long productSizeId) {
        Query query = Query.query(Criteria.where("memberId").is(memberId)
                .and("productId").is(productId)
                .and("productSizeId").is(productSizeId));

        // 해당하는 wish list 찾아서 삭제
        WishList wishList = mongoTemplate.findAndRemove(query, WishList.class);
        if(wishList == null) { // 삭제된 게 없다 -> 기존에 존재하지 않았다 -> 새로 생성
            wishList = mongoTemplate.save(WishList.create(memberId, productId, productSizeId));
        }
        return wishList;
    }
}
