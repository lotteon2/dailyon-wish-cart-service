package com.dailyon.wishcartservice.cart.service;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.document.Cart;
import static com.dailyon.wishcartservice.cart.document.Cart.CartItem;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    @Transactional
    public Cart upsert(Long memberId, UpsertCartRequest upsertCartRequest) {
        Optional<Cart> cart = cartRepository.findById(memberId);
        if(cart.isEmpty()) { // cart document 자체가 없을 때는 document를 새로 생성
            if(upsertCartRequest.getLastMemberCode() == null) {
                return cartRepository.save(Cart.init(memberId,
                        upsertCartRequest.getProductId(),
                        upsertCartRequest.getProductSizeId(),
                        upsertCartRequest.getQuantity()));
            } else {
                return cartRepository.save(Cart.init(memberId,
                        upsertCartRequest.getProductId(),
                        upsertCartRequest.getProductSizeId(),
                        upsertCartRequest.getQuantity(),
                        upsertCartRequest.getLastMemberCode()));
            }
        } else {
            // cartItems 안에 해당 cartItem이 존재한다면 값 update 후 return
            for(Cart.CartItem cartItem: cart.get().getCartItems()) {
                if(cartItem.getProductId().equals(upsertCartRequest.getProductId()) &&
                   cartItem.getProductSizeId().equals(upsertCartRequest.getProductSizeId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + upsertCartRequest.getQuantity());
                    Optional.ofNullable(upsertCartRequest.getLastMemberCode()).ifPresent(cartItem::setLastMemberCode);

                    return cartRepository.save(cart.get());
                }
            }

            // cartItem 안에 해당 cartItem이 존재하지 않는다면 create
            if(upsertCartRequest.getLastMemberCode() == null) {
                cart.get().getCartItems().add(Cart.CartItem.create(
                        upsertCartRequest.getProductId(),
                        upsertCartRequest.getProductSizeId(),
                        upsertCartRequest.getQuantity()));
            } else {
                cart.get().getCartItems().add(Cart.CartItem.create(
                        upsertCartRequest.getProductId(),
                        upsertCartRequest.getProductSizeId(),
                        upsertCartRequest.getQuantity(),
                        upsertCartRequest.getLastMemberCode()));
            }
            return cartRepository.save(cart.get());
        }
    }

    @Transactional
    public void deleteCarts(Long memberId, DeleteCartListRequest request) {
        cartRepository.delete(memberId, request.getRequests());
    }

    public Page<CartItem> readPages(Long memberId, Pageable pageable) {
        return cartRepository.readPages(memberId, pageable);
    }
}
