package org.work.springtestexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.springtestexample.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
