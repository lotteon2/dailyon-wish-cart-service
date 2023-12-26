package com.dailyon.wishcartservice.wishlist.facade;

import com.dailyon.wishcartservice.common.feign.client.ProductFeignClient;
import com.dailyon.wishcartservice.common.feign.request.ReadWishCartProductRequest;
import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse;
import com.dailyon.wishcartservice.wishlist.document.WishList;
import com.dailyon.wishcartservice.wishlist.dto.request.ToggleWishListRequest;
import com.dailyon.wishcartservice.wishlist.dto.response.ReadWishListFromProductResponse;
import com.dailyon.wishcartservice.wishlist.dto.response.ReadWishListPageResponse;
import com.dailyon.wishcartservice.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WishListFacade {
    private final WishListService wishListService;
    private final ProductFeignClient productFeignClient;

    public WishList toggleWishList(Long memberId, ToggleWishListRequest request) {
        return wishListService.toggleWishList(memberId, request);
    }

    public ReadWishListPageResponse readWishLists(Long memberId, Long targetId, Pageable pageable) {
        boolean isMine = isMine(memberId, targetId);
        Page<WishList> pages = wishListService.readWishListPages(isMine ? memberId : targetId, pageable);
        ReadWishCartProductMapResponse response = productFeignClient.readWishCartProducts(
                pages.stream().map(ReadWishCartProductRequest::fromEntity).collect(Collectors.toList())
        ).getBody();

        return ReadWishListPageResponse.create(isMine, pages, response);
    }

    public ReadWishListFromProductResponse readWishListFromProduct(Long memberId, Long productId) {
        return ReadWishListFromProductResponse.fromEntity(wishListService.readWishListFromProduct(memberId, productId));
    }

    private boolean isMine(Long memberId, Long targetId) {
        return targetId == null || memberId.equals(targetId);
    }
}
