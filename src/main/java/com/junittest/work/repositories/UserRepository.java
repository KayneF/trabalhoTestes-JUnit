package com.junittest.work.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junittest.work.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
