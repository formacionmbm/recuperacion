package com.cursopoo.recuperacion.api;

import com.cursopoo.recuperacion.buscador.intefaces.Search;
import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestSearchLibraries {
//ESTA
    private final Search search;

    @GetMapping("/l")
    public List<Library> findAll(){
        log.info("[findAll]");
        return search.findAllLibraries();
    }

    @PostMapping("/li/s")
    public List<Library> search(@RequestParam("text") String texto){
        log.info("[search]");
        log.debug("[texto:{}",texto);

        List<Library> libraries= search.findLibrariesByTexto(texto);
        log.debug("Libraries find: {}",libraries);

        return libraries;
    }

    @GetMapping("/ca")
    public List<Category> findAllCategories(){
        log.info("[findAllCategories]");
        return search.findAllCategories();
    }
}
