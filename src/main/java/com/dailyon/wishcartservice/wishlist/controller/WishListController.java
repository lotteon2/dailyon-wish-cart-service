package com.dailyon.wishcartservice.wishlist.controller;

import com.dailyon.wishcartservice.wishlist.dto.response.ReadWishListPageResponse;
import com.dailyon.wishcartservice.wishlist.facade.WishListFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
