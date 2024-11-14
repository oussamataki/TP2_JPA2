package org.example.jpa2;

import org.example.jpa2.entities.*;
import org.example.jpa2.repositories.ConsultationRepository;
import org.example.jpa2.repositories.MedcinRepository;
import org.example.jpa2.repositories.PatientRepository;
import org.example.jpa2.repositories.RendezVousRepository;
import org.example.jpa2.service.HospitalServiceImpl;
import org.example.jpa2.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class Jpa2Application {

    public static void main(String[] args) {
        SpringApplication.run(Jpa2Application.class, args);
    }
    @Bean
    CommandLineRunner start(IHospitalService hospitalService) {
        return args -> {
            Stream.of("SAID", "KHALID", "JAAD", "YOUSSEF").forEach(nom -> {
                Medcin medcin = new Medcin();
                medcin.setNom(nom);
                medcin.setEmail(nom + "@gmail.com");
                medcin.setSpecialite(Math.random() > 0.5 ? "Dentiste" : "Ophtalmologiste");
                hospitalService.saveMedcin(medcin);
            });
            Stream.of("Amine", "Mohamed", "Hassan", "Yassine").forEach(nom -> {
                Patient patient = new Patient();
                patient.setNom(nom);
                patient.setDateNaissance(new Date());
                patient.setMalade(false);
                hospitalService.savePatient(patient);
            });
            Patient patient = hospitalService.findPatientById(1L);
            Patient patient1 = hospitalService.findPatientByNom("Amine");

            Medcin medcin = hospitalService.findMedcinById(1L);
            Medcin medcin1 = hospitalService.findMedcinByNom("SAID");

            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setPatient(patient);
            rendezVous.setMedcin(medcin);
            rendezVous.setStatusRDV(StatusRDV.PENDING);
            hospitalService.saveRendezVous(rendezVous);

            RendezVous rendezVous1 = hospitalService.findRendezVousById(1L);
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(new Date());
            consultation.setRendezVous(rendezVous1);
            consultation.setRapport("RAS");
            hospitalService.saveConsultation(consultation);



        };
    }

}