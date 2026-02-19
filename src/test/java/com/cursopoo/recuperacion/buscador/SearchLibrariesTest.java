package com.cursopoo.recuperacion.buscador;

import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;
import com.cursopoo.recuperacion.exceptions.AppException;
import com.cursopoo.recuperacion.exceptions.ServiceException;
import com.cursopoo.recuperacion.repositories.CategoryRepository;
import com.cursopoo.recuperacion.repositories.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchLibrariesTest {


    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SearchLibraries searchLibraries;


    // =============================
    // Test findAllLibraries
    // =============================
    @Test
    void findAllLibraries_ok() {
        // Arrange
        Library lib1 = new Library(1L,"Group1", "Lib1", "1.0", new ArrayList<>());
        Library lib2 = new Library(2L,"Group2", "Lib2", "2.0", new ArrayList<>());
        List<Library> allLibraries = List.of(lib1, lib2);

        when(libraryRepository.findAll()).thenReturn(allLibraries);

        // Act
        List<Library> result = searchLibraries.findAllLibraries();

        // Assert
        assertThat(result).hasSize(2).contains(lib1, lib2);
        verify(libraryRepository, times(1)).findAll();
    }

    @Test
    void findAllLibraries_ko() {
        // Arrange
        when(libraryRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(ServiceException.class,
                () -> searchLibraries.findAllLibraries());
        verify(libraryRepository, times(1)).findAll();
    }

    // =============================
    // Test findAllCategories
    // =============================
    @Test
    void findAllCategories_ok() {
        // Arrange
        Category catFramework = new Category(1L, "FRAMEWORK");
        Category catSecurity = new Category(2L, "SECURITY");
        List<Category> allCategories = List.of(catFramework, catSecurity);

        when(categoryRepository.findAll()).thenReturn(allCategories);

        // Act
        List<Category> result = searchLibraries.findAllCategories();

        // Assert
        assertThat(result).hasSize(2).contains(catFramework, catSecurity);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findAllCategories_ko() {
        // Arrange
        when(categoryRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(ServiceException.class,
                () -> searchLibraries.findAllCategories());
        verify(categoryRepository, times(1)).findAll();
    }

    // =============================
    // Test findLibrariesByTexto
    // =============================
    @Test
    void findLibrariesByTexto_ok() {
        // Arrange
        String texto = "Framework Security";

        Category catFramework = new Category(1L, "FRAMEWORK");
        Category catSecurity = new Category(2L, "SECURITY");

        Library lib1 = new Library(1L, "com.spring","Spring Boot", "3.2.0", List.of(catFramework));
        Library lib2 = new Library(2L,"org.invented", "Keycloak", "22.0", List.of(catSecurity));

        when(categoryRepository.findByName("FRAMEWORK")).thenReturn(Optional.of(catFramework));
        when(categoryRepository.findByName("SECURITY")).thenReturn(Optional.of(catSecurity));

        when(libraryRepository.findByCategory(catFramework)).thenReturn(List.of(lib1));
        when(libraryRepository.findByCategory(catSecurity)).thenReturn(List.of(lib2));

        // Act
        List<Library> result = searchLibraries.findLibrariesByTexto(texto);

        // Assert
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(lib1, lib2);

        verify(categoryRepository, times(1)).findByName("FRAMEWORK");
        verify(categoryRepository, times(1)).findByName("SECURITY");
        verify(libraryRepository, times(1)).findByCategory(catFramework);
        verify(libraryRepository, times(1)).findByCategory(catSecurity);
    }

    @Test
    void findLibrariesByTexto_ko() {
        // Arrange
        String texto = "";

        Category catFramework = new Category(1L, "FRAMEWORK");
        Library lib1 = new Library(1L, "com.spring","Spring Boot", "3.2.0", List.of(catFramework));

        List<Library> libraries = List.of(lib1);

        when(libraryRepository.findAll()).thenReturn(libraries);

        // Act
        List<Library> result = searchLibraries.findLibrariesByTexto(texto);

        // Assert
        assertThat(result).hasSize(1).containsExactlyInAnyOrder(lib1);
        verify(libraryRepository, times(1)).findAll();
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void findLibrariesByTexto_ko_exception() {
        // Arrange
        String texto = "Framework";
        Category catFramework = new Category(1L, "FRAMEWORK");

        when(categoryRepository.findByName("FRAMEWORK")).thenReturn(Optional.of(catFramework));
        when(libraryRepository.findByCategory(catFramework)).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(ServiceException.class,
                () -> searchLibraries.findLibrariesByTexto(texto));

        verify(categoryRepository, times(1)).findByName("FRAMEWORK");
        verify(libraryRepository, times(1)).findByCategory(catFramework);
    }

}