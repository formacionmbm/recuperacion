package com.cursopoo.recuperacion.repositories;

import com.cursopoo.recuperacion.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.name= :nombre")
    Optional<Category> findByNombre(String name);
}
