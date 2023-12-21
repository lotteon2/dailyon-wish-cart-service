package com.dailyon.wishcartservice.cart.controller;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> upsertCart(@RequestHeader(name = "memberId") Long memberId,
                                           @Valid @RequestBody UpsertCartRequest upsertCartRequest) {
        cartService.upsert(memberId, upsertCartRequest);
        return ResponseEntity.ok().build();
    }
}
