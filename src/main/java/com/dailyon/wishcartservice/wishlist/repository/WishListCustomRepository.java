package com.dailyon.wishcartservice.wishlist.repository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListCustomRepository {
    Page<WishList> readWishListPages(Long memberId, Pageable pageable);
}
