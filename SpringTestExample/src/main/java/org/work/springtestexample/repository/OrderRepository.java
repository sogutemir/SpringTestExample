package org.work.springtestexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.springtestexample.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
