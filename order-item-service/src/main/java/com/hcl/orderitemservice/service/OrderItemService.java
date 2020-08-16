package com.hcl.orderitemservice.service;

import com.hcl.orderitemservice.repository.OrderItemRepository;
import com.hcl.orderitemservice.repository.entity.OrderItem;
import com.hcl.orderitemservice.request.OrderItemRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemService {

     private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderItem create(final OrderItemRequest request) {
        return orderItemRepository.save(new OrderItem());
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderItem getById(final UUID id) {
        return orderItemRepository.findById(id).get();
    }

}
