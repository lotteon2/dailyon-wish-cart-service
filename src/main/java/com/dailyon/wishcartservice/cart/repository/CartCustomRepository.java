package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;

public interface CartCustomRepository {
    void upsert(Long memberId, UpsertCartRequest upsertCartRequest);
}
