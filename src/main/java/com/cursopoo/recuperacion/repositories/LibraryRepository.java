package com.cursopoo.recuperacion.repositories;

import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library,Long> {
    //ESTA
    @Query("SELECT l FROM Library l JOIN l.categories c WHERE c = :category")
    List<Library> findByCategory(Category category);
}
