package com.junittest.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junittest.work.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
