package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private String serialNumber;
    private String capacity;
    private String dimensions;
    private Date installationDate;
    @ManyToOne
    private User owner;
    @ManyToMany
    @JoinTable(
        name = "ro_system_component",
        joinColumns = @JoinColumn(name = "ro_system_id"),
        inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private List<Component> components = new ArrayList<>();

    @OneToMany
    private List<Alert> alerts = new ArrayList<>();

}
