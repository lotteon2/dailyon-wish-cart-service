package com.dailyon.wishcartservice.cart.repository;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import static com.dailyon.wishcartservice.cart.document.Cart.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartCustomRepository {
    void upsert(Long memberId, UpsertCartRequest upsertCartRequest);

    void delete(Long memberId, List<DeleteCartRequest> requests);

    Page<CartItem> readPages(Long memberId, Pageable pageable);
}
