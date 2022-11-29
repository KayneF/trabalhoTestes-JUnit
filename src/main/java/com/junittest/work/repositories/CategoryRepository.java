package com.junittest.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junittest.work.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
