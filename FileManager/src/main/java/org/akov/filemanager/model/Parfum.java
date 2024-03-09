package org.akov.filemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parfum")
@Data
public class Parfum {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String isShowed;
    private Integer prix;
    @Column(columnDefinition = "TEXT")
    private String description;

    private String photo;

}
