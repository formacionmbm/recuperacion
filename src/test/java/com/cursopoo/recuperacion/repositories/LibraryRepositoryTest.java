package com.cursopoo.recuperacion.repositories;

import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByCategory_ok() {

        // Arrange: Crear categoría
        Category framework = new Category();
        framework.setId(1000);
        framework.setName("Framework");
        categoryRepository.save(framework);

        // Crear packages
        Library lib1 = new Library();
        lib1.setId(1000l);
        lib1.setGroupId("org.springframework");
        lib1.setArtifacId("spring-boot-starter-web");
        lib1.setVersion("3.2.0");
        lib1.setCategories(List.of(framework));

        Library lib2 = new Library();
        lib2.setId(1001l);
        lib2.setGroupId("org.hibernate");
        lib2.setArtifacId("hibernate-core");
        lib2.setVersion("6.4.0");
        lib2.setCategories(List.of()); // sin category

        libraryRepository.save(lib1);
        libraryRepository.save(lib2);

        // Act
        List<Library> result = libraryRepository.findByCategory(framework);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGroupId()).isEqualTo("org.springframework");
    }

    @Test
    void findByCategory_ko() {

        // Arrange
        Category emptyCategory = new Category();
        emptyCategory.setId(2000);
        emptyCategory.setName("Empty");
        categoryRepository.save(emptyCategory);

        // Act
        List<Library> result = libraryRepository.findByCategory(emptyCategory);

        // Assert
        assertThat(result).isEmpty();
    }
}
