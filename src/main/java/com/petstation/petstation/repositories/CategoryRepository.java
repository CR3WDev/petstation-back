package com.petstation.petstation.repositories;

import com.petstation.petstation.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAll(Specification<Category> spec, Pageable pageable);

}
