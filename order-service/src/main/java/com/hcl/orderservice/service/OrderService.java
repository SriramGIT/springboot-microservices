package com.hcl.orderservice.service;

import com.hcl.orderservice.feign.OrderItemDTO;
import com.hcl.orderservice.feign.OrderItemFeignClient;
import com.hcl.orderservice.repository.OrderRepository;
import com.hcl.orderservice.repository.entity.Order;
import com.hcl.orderservice.request.OrderRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

     private final OrderRepository orderRepository;
     private final OrderItemFeignClient orderItemFeignClient;

    public OrderService(OrderRepository orderRepository, OrderItemFeignClient orderItemFeignClient) {
        this.orderRepository = orderRepository;
        this.orderItemFeignClient = orderItemFeignClient;
    }


    @Transactional
    public Order create(final OrderRequest request) {
        return orderRepository.save(Order.builder()
                .orderDate(LocalDateTime.now())
                .shippingAddress(request.getShippingAddress())
                .orderTotal(getOrderTotal(request.getOrderItemIds()))
                .orderItems(request.getOrderItemIds())
                .customerName(request.getCustomerName())
                .build()
        );
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    // non api
    private int getOrderTotal(final Set<UUID> ids) {
        int total = 0;
        for(UUID id: ids) {
            OrderItemDTO orderItem = orderItemFeignClient.getOrderItems(id);
            total += orderItem.getSalePrice() * orderItem.getQuantity();
        }
        return total;
    }
}
