package org.example.jpa2.repositories;

import org.example.jpa2.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByNom(String nom);
}
