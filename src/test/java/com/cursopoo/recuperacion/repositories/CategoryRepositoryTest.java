package com.cursopoo.recuperacion.repositories;

import com.cursopoo.recuperacion.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByName_ok() {

        // Arrange
        Category category = new Category();
        category.setId(1000);
        category.setName("BBDD");
        categoryRepository.save(category);

        // Act
        Optional<Category> result = categoryRepository.findByName("BBDD");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("BBDD");
    }

    @Test
    void findByName_ko() {

        // Act
        Optional<Category> result = categoryRepository.findByName("NoExiste");

        // Assert
        assertThat(result).isEmpty();
    }
}