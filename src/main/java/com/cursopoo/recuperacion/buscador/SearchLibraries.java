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

    private final LibraryRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Library> findAllLibraries() {
        log.info("[findAllLibraries]");
        try{

            return List.of();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public List<Category> findAllCategories() {
        log.info("[findAllCategories]");
        try{
            Category catFramework = new Category(1L, "DB");
            Category catSecurity = new Category(2L, "SECURITY");
            List<Category> allCategories = List.of(catFramework, catSecurity);

            return allCategories;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new AppException(e.getMessage());
        }
    }

    public List<Library> findLibrariesByTexto(String texto){
        log.info("[findLibrariesByTexto]");
        log.debug("[texto:{}]",texto);

        List<Library> libraries=new ArrayList<>();

        try {


            texto = procesarTexto(texto);



            String[] nameCategories = texto.split(" ");

            for (String nameCategory : nameCategories) {
                log.debug("Buscamos la categoría:{}",nameCategory);
                Optional<Category> category = categoryRepository.findByNombre(nameCategory);
                if(category.isPresent()){
                    libraries.addAll(repository.findByCategory(category.get()));
                }
            }
            log.debug("[libraries:{}]",libraries);
            return libraries;

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new AppException(e.getMessage());
        }
    }
}
