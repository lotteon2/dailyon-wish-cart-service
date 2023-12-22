package com.dailyon.wishcartservice.cart.facade;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.dto.request.UpsertCartRequest;
import com.dailyon.wishcartservice.cart.dto.response.ReadCartPageResponse;
import com.dailyon.wishcartservice.cart.entity.Cart;
import static com.dailyon.wishcartservice.cart.entity.Cart.CartItem;
import com.dailyon.wishcartservice.cart.service.CartService;
import com.dailyon.wishcartservice.common.feign.client.ProductFeignClient;
import com.dailyon.wishcartservice.common.feign.request.ReadWishCartProductRequest;
import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    public ReadCartPageResponse read(Long memberId, Pageable pageable) {
        Page<CartItem> cartItems = cartService.readPages(memberId, pageable);

        List<ReadWishCartProductRequest> requests = cartItems.stream()
                .map(ReadWishCartProductRequest::fromEntity)
                .collect(Collectors.toList());

        ReadWishCartProductMapResponse response = productFeignClient.readWishCartProducts(requests).getBody();

        return ReadCartPageResponse.create(cartItems, response);
    }
}
