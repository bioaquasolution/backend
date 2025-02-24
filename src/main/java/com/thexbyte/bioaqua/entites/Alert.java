package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; 
    private String severity;
    private String content;
    private Date alertDate;
    @JsonIgnore
    @ManyToOne
    private RoSystem roSystem ;
}
