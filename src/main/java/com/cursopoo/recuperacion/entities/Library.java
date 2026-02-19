package com.cursopoo.recuperacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    @Id
    private Long id;
    private String groupId;
    private String artifacId;
    private String version;

    @ManyToMany
    @JoinTable(
            name = "LIBRARY_CATEGORY",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")

    )
    List<Category> categories;


}
