package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String name;
    private String type; 
    private double price;
    @JsonIgnore
    @ManyToMany(mappedBy = "components")
    private List<RoSystem> roSystems = new ArrayList<>();
}
