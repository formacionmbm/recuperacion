package com.cursopoo.recuperacion.repositories;

import com.cursopoo.recuperacion.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    //ESTA
    Optional<Category> findByName(String name);

}
