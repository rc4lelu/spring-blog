package org.springblog.repository;

import org.springblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   public Optional<User> findByUserName(String username);
}
