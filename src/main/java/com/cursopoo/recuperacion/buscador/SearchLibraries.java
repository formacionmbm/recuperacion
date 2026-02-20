package com.cursopoo.recuperacion.buscador;

import com.cursopoo.recuperacion.buscador.intefaces.Search;
import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;
import com.cursopoo.recuperacion.exceptions.AppException;
import com.cursopoo.recuperacion.exceptions.ServiceException;
import com.cursopoo.recuperacion.repositories.CategoryRepository;
import com.cursopoo.recuperacion.repositories.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cursopoo.recuperacion.buscador.util.TextUtilities.procesarTexto;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchLibraries implements Search {
    //ESTA
    private final LibraryRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Library> findAllLibraries() {
        log.info("[findAllLibraries]");
        try{

            return  repository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override

    public List<Category> findAllCategories() {
        log.info("[findAllCategories]");
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Library> findLibrariesByTexto(String texto) {
        log.info("[findLibrariesByTexto]");
        log.debug("[texto:{}]", texto);

        try {
            if (texto == null || texto.trim().isEmpty()) {
                throw new ServiceException("Texto no puede ser nulo o vacío");
            }

            texto = procesarTexto(texto);
            String[] nameCategories = texto.split(" ");

            List<Library> libraries = new ArrayList<>();

            for (String nameCategory : nameCategories) {
                Optional<Category> category = categoryRepository.findByName(nameCategory);

                if (category.isPresent()) {
                    libraries.addAll(repository.findByCategory(category.get()));
                }
            }

            return libraries;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }
}
