package org.example.jpa2.repositories;

import org.example.jpa2.entities.Medcin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedcinRepository extends JpaRepository<Medcin, Long> {
    Medcin findByNom(String nom);
}
