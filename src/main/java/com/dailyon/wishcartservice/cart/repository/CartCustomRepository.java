package com.dailyon.wishcartservice.cart.repository;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;

import java.util.List;

public interface CartCustomRepository {
    void upsert(Long memberId, UpsertCartRequest upsertCartRequest);

    void delete(Long memberId, List<DeleteCartRequest> requests);
}
