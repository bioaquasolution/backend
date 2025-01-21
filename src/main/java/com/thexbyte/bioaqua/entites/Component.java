package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String reference; // Unique reference for the component
    private String name;
    private String type; // e.g., "Filter", "Pump"
    private String description;
    private double price;
    @JsonIgnore
    @ManyToMany(mappedBy = "components")
    private List<RoSystem> roSystems = new ArrayList<>();
}
