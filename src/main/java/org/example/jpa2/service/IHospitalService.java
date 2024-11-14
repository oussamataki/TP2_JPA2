package org.example.jpa2.service;

import org.example.jpa2.entities.Consultation;
import org.example.jpa2.entities.Medcin;
import org.example.jpa2.entities.Patient;
import org.example.jpa2.entities.RendezVous;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medcin saveMedcin(Medcin medcin);
    RendezVous saveRendezVous(RendezVous rendezVous);
    Consultation saveConsultation(Consultation consultation);
    Patient findPatientById(Long id);
    Medcin findMedcinById(Long id);
    Patient findPatientByNom(String nom);
    Medcin findMedcinByNom(String nom);
    RendezVous findRendezVousById(Long id);
}
