package org.example.jpa2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Medcin {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;
    @OneToMany(mappedBy = "medcin", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
