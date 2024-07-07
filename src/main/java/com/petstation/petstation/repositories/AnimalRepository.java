package com.petstation.petstation.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.petstation.petstation.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    Page<Animal> findAll(Specification<Animal> spec, Pageable pageable);

}
