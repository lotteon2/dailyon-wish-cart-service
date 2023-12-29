package com.dailyon.wishcartservice.wishlist.service;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import com.dailyon.wishcartservice.wishlist.dto.request.ToggleWishListRequest;
import com.dailyon.wishcartservice.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;

    public Page<WishList> readWishListPages(Long memberId, Pageable pageable) {
        return wishListRepository.readWishListPages(memberId, pageable);
    }

    public WishList toggleWishList(Long memberId, ToggleWishListRequest request) {
        return wishListRepository.toggleWishList(memberId, request.getProductId(), request.getProductSizeId());
    }

    public List<WishList> readWishListFromProduct(Long memberId, Long productId) {
        return wishListRepository.findByMemberIdAndProductId(memberId, productId);
    }
}
