package com.dailyon.wishcartservice.cart.service;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void upsert(Long memberId, UpsertCartRequest upsertCartRequest) {
        cartRepository.upsert(memberId, upsertCartRequest);
    }
}
