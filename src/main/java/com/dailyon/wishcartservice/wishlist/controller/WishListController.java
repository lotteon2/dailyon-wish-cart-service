package com.dailyon.wishcartservice.wishlist.controller;

import com.dailyon.wishcartservice.wishlist.dto.request.ToggleWishListRequest;
import com.dailyon.wishcartservice.wishlist.dto.response.ReadWishListFromProductResponse;
import com.dailyon.wishcartservice.wishlist.dto.response.ReadWishListPageResponse;
import com.dailyon.wishcartservice.wishlist.facade.WishListFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/wish-list")
@RestController
@RequiredArgsConstructor
public class WishListController {
    private final WishListFacade wishListFacade;

    @GetMapping
    public ResponseEntity<ReadWishListPageResponse> readWishList(
            @RequestHeader(name = "memberId") Long memberId,
            @RequestHeader(name = "targetId", required = false) Long targetId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(wishListFacade.readWishLists(memberId, targetId, pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ReadWishListFromProductResponse> readWishListFromProduct(
            @RequestHeader(name = "memberId") Long memberId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(wishListFacade.readWishListFromProduct(memberId, productId));
    }

    @PutMapping
    public ResponseEntity<Void> toggleWishList(@RequestHeader(name = "memberId") Long memberId,
                                               @Valid @RequestBody ToggleWishListRequest request) {
        wishListFacade.toggleWishList(memberId, request);
        return ResponseEntity.ok().build();
    }
}
