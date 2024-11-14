package org.example.jpa2.service;

import jakarta.transaction.Transactional;
import org.example.jpa2.entities.Consultation;
import org.example.jpa2.entities.Medcin;
import org.example.jpa2.entities.Patient;
import org.example.jpa2.entities.RendezVous;
import org.example.jpa2.repositories.ConsultationRepository;
import org.example.jpa2.repositories.MedcinRepository;
import org.example.jpa2.repositories.PatientRepository;
import org.example.jpa2.repositories.RendezVousRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {
    private PatientRepository patientRepository;
    private MedcinRepository medcinRepository;
    private RendezVousRepository rendezVousRepository;
    private ConsultationRepository consultationRepository;

    public HospitalServiceImpl(PatientRepository patientRepository, MedcinRepository medcinRepository, RendezVousRepository rendezVousRepository, ConsultationRepository consultationRepository) {
        this.patientRepository = patientRepository;
        this.medcinRepository = medcinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.consultationRepository = consultationRepository;
    }
    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medcin saveMedcin(Medcin medcin) {
        return medcinRepository.save(medcin);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Medcin findMedcinById(Long id) {
        return medcinRepository.findById(id).get();
    }

    @Override
    public Patient findPatientByNom(String nom) {
        return patientRepository.findByNom(nom);
    }

    @Override
    public Medcin findMedcinByNom(String nom) {
        return medcinRepository.findByNom(nom);
    }

    @Override
    public RendezVous findRendezVousById(Long id) {
        return rendezVousRepository.findById(id).get();
    }
}
