package com.dailyon.wishcartservice.wishlist.service;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import com.dailyon.wishcartservice.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;

    public Page<WishList> readWishListPages(Long memberId, Pageable pageable) {
        return wishListRepository.readWishListPages(memberId, pageable);
    }
}