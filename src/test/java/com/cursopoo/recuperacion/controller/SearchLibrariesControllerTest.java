package com.cursopoo.recuperacion.controller;


import com.cursopoo.recuperacion.buscador.intefaces.Search;
import com.cursopoo.recuperacion.entities.Library;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(SearchLibrariesController.class)
class SearchLibrariesControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Search search; // Mockeamos la dependencia del controller

    // =============================
    // GET /
    // =============================
    @Test
    @DisplayName("GET / debe devolver la vista t_libraries")
    void testGoToSearchForm() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("t_libraries"))
                .andExpect(model().attributeExists("listado"));
    }

    // =============================
    // POST /
    // =============================
    @Test
    @DisplayName("POST / search debe agregar elementos al modelo")
    void testSearch() throws Exception {
        // Arrange
        Library lib1 = new Library();
        lib1.setId(1L);
        lib1.setGroupId("org.springframework");
        lib1.setArtifacId("spring-boot-starter-web");
        lib1.setVersion("3.2.0");

        List<Library> results = List.of(lib1);

        when(search.findLibrariesByTexto("Framework")).thenReturn(results);

        // Act & Assert
        mockMvc.perform(post("/")
                        .param("text", "Framework"))
                .andExpect(status().isOk())
                .andExpect(view().name("t_libraries"))
                .andExpect(model().attributeExists("listado"))
                .andExpect(model().attributeExists("elements"))
                .andExpect(model().attribute("elements", results));

        verify(search, times(1)).findLibrariesByTexto("Framework");
    }
}

