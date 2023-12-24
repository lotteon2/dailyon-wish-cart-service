package com.dailyon.wishcartservice.cart.service;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpdateCartRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.document.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    @Transactional
    public Cart upsert(Long memberId, UpsertCartRequest upsertCartRequest) {
        return cartRepository.upsert(memberId,
                upsertCartRequest.getProductId(),
                upsertCartRequest.getProductSizeId(),
                upsertCartRequest.getQuantity(),
                upsertCartRequest.getLastMemberCode());
    }

    @Transactional
    public void deleteCarts(Long memberId, DeleteCartListRequest request) {
        cartRepository.delete(memberId, request.getRequests());
    }

    public Page<Cart> readPages(Long memberId, Pageable pageable) {
        return cartRepository.readPages(memberId, pageable);
    }

    @Transactional
    public void update(Long memberId, UpdateCartRequest updateCartRequest) {
        cartRepository.update(memberId,
                updateCartRequest.getProductId(),
                updateCartRequest.getProductSizeId(),
                updateCartRequest.getQuantity());
    }
}
