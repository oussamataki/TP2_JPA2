package org.example.jpa2.web;

import org.example.jpa2.entities.Patient;
import org.example.jpa2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientRestController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping("/Patients")
    public List<Patient> list() {
        return patientRepository.findAll();
    }
}
