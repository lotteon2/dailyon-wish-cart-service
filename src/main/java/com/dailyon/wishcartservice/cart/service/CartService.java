package com.dailyon.wishcartservice.cart.service;

import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.entity.Cart;
import com.dailyon.wishcartservice.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
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
        if(cart.isEmpty()) {
            return cartRepository.save(Cart.init(memberId,
                    upsertCartRequest.getProductId(),
                    upsertCartRequest.getProductSizeId(),
                    upsertCartRequest.getQuantity(),
                    upsertCartRequest.getLastMemberCode()
            ));
        } else {
            for(Cart.CartItem cartItem: cart.get().getCartItems()) {
                if(cartItem.getProductId().equals(upsertCartRequest.getProductId()) &&
                   cartItem.getProductSizeId().equals(upsertCartRequest.getProductSizeId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + upsertCartRequest.getQuantity());
                    Optional.ofNullable(upsertCartRequest.getLastMemberCode()).ifPresent(cartItem::setLastMemberCode);

                    return cartRepository.save(cart.get());
                }
            }

            cart.get().getCartItems().add(Cart.CartItem.init(
                    upsertCartRequest.getProductId(),
                    upsertCartRequest.getProductSizeId(),
                    upsertCartRequest.getQuantity(),
                    upsertCartRequest.getLastMemberCode()));

            return cartRepository.save(cart.get());
        }
    }
}
