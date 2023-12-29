package com.dailyon.wishcartservice.cart.kafka;

import static com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest.DeleteCartRequest;

import com.dailyon.wishcartservice.cart.dto.request.DeleteCartListRequest;
import com.dailyon.wishcartservice.cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dailyon.domain.order.kafka.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApprovePaymentHandler {
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "approve-payment")
    public void consume(String message, Acknowledgment ack) {
        try {
            OrderDTO orderDto = objectMapper.readValue(message, OrderDTO.class);
            if(orderDto.getOrderType() != OrderDTO.OrderType.CART) return;

            DeleteCartListRequest request = DeleteCartListRequest.builder()
                    .requests(orderDto.getProductInfos().stream()
                            .map(DeleteCartRequest::fromDto)
                            .collect(Collectors.toList()))
                    .build();

            cartService.deleteCarts(orderDto.getMemberId(), request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ack.acknowledge();
        }
    }
}
