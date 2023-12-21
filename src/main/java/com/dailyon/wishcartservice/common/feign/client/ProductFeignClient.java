package com.dailyon.wishcartservice.common.feign.client;

import com.dailyon.wishcartservice.common.config.FeignClientConfig;
import com.dailyon.wishcartservice.common.feign.request.ReadWishCartProductRequest;
import com.dailyon.wishcartservice.common.feign.response.ReadWishCartProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "${endpoint.product-service}",
        configuration = FeignClientConfig.class
)
public interface ProductFeignClient {
    @PostMapping(value = "/clients/products/wish-cart")
    ResponseEntity<ReadWishCartProductListResponse> readWishCartProducts(@RequestBody List<ReadWishCartProductRequest> requests);
}
