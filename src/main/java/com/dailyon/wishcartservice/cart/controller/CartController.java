package com.dailyon.wishcartservice.cart.controller;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.dto.response.ReadCartPageResponse;
import com.dailyon.wishcartservice.cart.facade.CartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartFacade cartFacade;

    @PostMapping
    public ResponseEntity<Void> upsertCart(@RequestHeader(name = "memberId") Long memberId,
                                           @Valid @RequestBody UpsertCartRequest upsertCartRequest) {
        cartFacade.upsert(memberId, upsertCartRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestHeader(name = "memberId") Long memberId,
                                           @Valid @RequestBody DeleteCartListRequest deleteCartListRequest) {
        cartFacade.delete(memberId, deleteCartListRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ReadCartPageResponse> readCart(
            @RequestHeader(name = "memberId") Long memberId,
            @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(cartFacade.read(memberId, pageable));
    }
}
