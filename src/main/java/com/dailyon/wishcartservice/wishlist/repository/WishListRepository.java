package com.dailyon.wishcartservice.wishlist.repository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends MongoRepository<WishList, String>, WishListCustomRepository {
    List<WishList> findByMemberIdAndProductId(Long memberId, Long productId);
}
