package com.dailyon.wishcartservice.cart.facade;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.dto.response.ReadCartListResponse;
import com.dailyon.wishcartservice.cart.entity.Cart;
import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;
import com.dailyon.wishcartservice.cart.service.CartService;
import com.dailyon.wishcartservice.common.feign.client.ProductFeignClient;
import com.dailyon.wishcartservice.common.feign.request.ReadWishCartProductRequest;
import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductListResponse;
import static com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductListResponse.ReadWishCartProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartFacade {
    private final CartService cartService;
    private final ProductFeignClient productFeignClient;

    public void upsert(Long memberId, UpsertCartRequest upsertCartRequest) {
        cartService.upsert(memberId, upsertCartRequest);
    }

    public void delete(Long memberId, DeleteCartListRequest deleteCartListRequest) {
        cartService.deleteCarts(memberId, deleteCartListRequest);
    }

    public ReadCartListResponse read(Long memberId) {
        return cartService.read(memberId).map(
                cart -> ReadCartListResponse.create(
                        cart, productFeignClient.readWishCartProducts(cart.getCartItems().stream()
                                .map(ReadWishCartProductRequest::fromEntity)
                                .collect(Collectors.toList())).getBody()
                )
        ).orElseGet(() -> ReadCartListResponse.builder().responses(new ArrayList<>()).build());
    }
}
