package org.work.springtestexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.springtestexample.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
