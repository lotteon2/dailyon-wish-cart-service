package com.dailyon.wishcartservice.cart.controller;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestHeader(name = "memberId") Long memberId,
                                           @Valid @RequestBody DeleteCartListRequest deleteCartListRequest) {
        cartService.deleteCarts(memberId, deleteCartListRequest);
        return ResponseEntity.ok().build();
    }
}
