package com.hcl.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.orderservice.repository.entity.Order;

import java.util.UUID;

@Repository
public interface OrderRepository  extends JpaRepository<Order, UUID> {

}
