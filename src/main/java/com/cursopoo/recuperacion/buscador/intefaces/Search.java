package com.cursopoo.recuperacion.buscador.intefaces;

import com.cursopoo.recuperacion.entities.Category;
import com.cursopoo.recuperacion.entities.Library;

import java.util.List;

public interface Search {
    //ESTA
    public List<Library> findAllLibraries();
    public List<Category> findAllCategories();
    public List<Library> findLibrariesByTexto(String texto);


}
