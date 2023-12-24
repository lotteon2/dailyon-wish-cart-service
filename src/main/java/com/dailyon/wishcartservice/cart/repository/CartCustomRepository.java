package com.dailyon.wishcartservice.cart.repository;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;

import com.dailyon.wishcartservice.cart.document.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartCustomRepository {
    Cart upsert(Long memberId, Long productId, Long productSizeId, Long quantity, String lastMemberCode);

    void delete(Long memberId, List<DeleteCartRequest> requests);

    Page<Cart> readPages(Long memberId, Pageable pageable);

    Cart update(Long memberId, Long productId, Long productSizeId, Long quantity);
}
