package com.dailyon.wishcartservice.wishlist.repository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends MongoRepository<WishList, String>, WishListCustomRepository {
}
