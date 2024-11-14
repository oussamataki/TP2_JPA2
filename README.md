# Project Title: JPA2 (Hospital) Application
## Creator
TAKI Oussama

## Description
This project is a simple JPA2 application that uses the following technologies:
- Java 17
- JPA2
- Maven
- Spring Boot

## Project Structure
The project is structured as follows:
- entities: Contains the entities that are mapped to the database tables.
- repositories: Contains the repositories that are used to interact with the database.
- services: Contains the services that are used to interact with the repositories.
- web: Contains the controllers that are used to interact with the services.

## Code Explanation
### Entities Explanation

#### `Medcin`
The `Medcin` entity represents a doctor in the system.

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medcin {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;
    
    @OneToMany(mappedBy = "medcin", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
```

- **Fields:**
    - `id`: Unique identifier for the doctor.
    - `nom`: Name of the doctor.
    - `email`: Email address of the doctor.
    - `specialite`: Specialty of the doctor (e.g., Dentist, Ophthalmologist).
    - `rendezVous`: Collection of appointments associated with the doctor.

#### `Consultation`
The `Consultation` entity represents a medical consultation.

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Date dateConsultation;
    private String rapport;
    
    @OneToOne
    private RendezVous rendezVous;
}
```

- **Fields:**
    - `id`: Unique identifier for the consultation.
    - `dateConsultation`: Date of the consultation.
    - `rapport`: Report of the consultation.
    - `rendezVous`: The appointment associated with the consultation.

#### `Patient`
The `Patient` entity represents a patient in the system.

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String nom;
    
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date dateNaissance;
    private Boolean malade;
    
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
```

- **Fields:**
    - `id`: Unique identifier for the patient.
    - `nom`: Name of the patient.
    - `dateNaissance`: Birth date of the patient.
    - `malade`: Indicates if the patient is currently ill.
    - `rendezVous`: Collection of appointments associated with the patient.

#### `RendezVous`
The `RendezVous` entity represents an appointment between a patient and a doctor.

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Date date;
    
    @Enumerated(EnumType.STRING)
    private StatusRDV statusRDV;
    
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patient patient;
    
    @ManyToOne
    private Medcin medcin;
    
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
}
```

- **Fields:**
    - `id`: Unique identifier for the appointment.
    - `date`: Date of the appointment.
    - `statusRDV`: Status of the appointment (e.g., PENDING, DONE, CANCELED).
    - `patient`: The patient associated with the appointment.
    - `medcin`: The doctor associated with the appointment.
    - `consultation`: The consultation associated with the appointment.

#### `StatusRDV`
The `StatusRDV` enum represents the status of an appointment.

```java
public enum StatusRDV {
    PENDING, DONE, CANCELED
}
```

- **Values:**
    - `PENDING`: The appointment is pending.
    - `DONE`: The appointment is completed.
    - `CANCELED`: The appointment is canceled.

### Repositories Explanation

#### `ConsultationRepository`
The `ConsultationRepository` interface is used to manage `Consultation` entities. It extends `JpaRepository`, providing CRUD operations and additional JPA functionalities.

```java
package org.example.jpa2.repositories;

import org.example.jpa2.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
```

- **Methods:**
    - Inherits all CRUD operations from `JpaRepository`.

#### `MedcinRepository`
The `MedcinRepository` interface is used to manage `Medcin` entities. It extends `JpaRepository` and includes a custom method to find a `Medcin` by their name.

```java
package org.example.jpa2.repositories;

import org.example.jpa2.entities.Medcin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedcinRepository extends JpaRepository<Medcin, Long> {
    Medcin findByNom(String nom);
}
```

- **Methods:**
    - `findByNom(String nom)`: Finds a `Medcin` by their name.

#### `PatientRepository`
The `PatientRepository` interface is used to manage `Patient` entities. It extends `JpaRepository` and includes a custom method to find a `Patient` by their name.

```java
package org.example.jpa2.repositories;

import org.example.jpa2.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByNom(String nom);
}
```

- **Methods:**
    - `findByNom(String nom)`: Finds a `Patient` by their name.

#### `RendezVousRepository`
The `RendezVousRepository` interface is used to manage `RendezVous` entities. It extends `JpaRepository`, providing CRUD operations and additional JPA functionalities.

```java
package org.example.jpa2.repositories;

import org.example.jpa2.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
```

- **Methods:**
    - Inherits all CRUD operations from `JpaRepository`.

### Services Explanation

#### `IHospitalService`
The `IHospitalService` interface defines the contract for the hospital service. It declares methods for managing `Medcin`, `Patient`, `RendezVous`, and `Consultation` entities.

```java
package org.example.jpa2.service;

import org.example.jpa2.entities.*;

public interface IHospitalService {
    Medcin saveMedcin(Medcin medcin);
    Patient savePatient(Patient patient);
    RendezVous saveRendezVous(RendezVous rendezVous);
    Consultation saveConsultation(Consultation consultation);
    Medcin findMedcinById(Long id);
    Patient findPatientById(Long id);
    RendezVous findRendezVousById(Long id);
    Medcin findMedcinByNom(String nom);
    Patient findPatientByNom(String nom);
}
```

- **Methods:**
    - `saveMedcin(Medcin medcin)`: Saves a `Medcin` entity.
    - `savePatient(Patient patient)`: Saves a `Patient` entity.
    - `saveRendezVous(RendezVous rendezVous)`: Saves a `RendezVous` entity.
    - `saveConsultation(Consultation consultation)`: Saves a `Consultation` entity.
    - `findMedcinById(Long id)`: Finds a `Medcin` by its ID.
    - `findPatientById(Long id)`: Finds a `Patient` by its ID.
    - `findRendezVousById(Long id)`: Finds a `RendezVous` by its ID.
    - `findMedcinByNom(String nom)`: Finds a `Medcin` by its name.
    - `findPatientByNom(String nom)`: Finds a `Patient` by its name.

#### `HospitalServiceImpl`
The `HospitalServiceImpl` class implements the `IHospitalService` interface. It provides the actual logic for managing `Medcin`, `Patient`, `RendezVous`, and `Consultation` entities using the respective repositories.

```java
package org.example.jpa2.service;

import org.example.jpa2.entities.*;
import org.example.jpa2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {

    @Autowired
    private MedcinRepository medcinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public Medcin saveMedcin(Medcin medcin) {
        return medcinRepository.save(medcin);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
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
    public Medcin findMedcinById(Long id) {
        return medcinRepository.findById(id).orElse(null);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public RendezVous findRendezVousById(Long id) {
        return rendezVousRepository.findById(id).orElse(null);
    }

    @Override
    public Medcin findMedcinByNom(String nom) {
        return medcinRepository.findByNom(nom);
    }

    @Override
    public Patient findPatientByNom(String nom) {
        return patientRepository.findByNom(nom);
    }
}
```

- **Methods:**
    - Implements all methods declared in the `IHospitalService` interface.
    - Uses the respective repositories to perform CRUD operations on the entities.

### `Jpa2Application` Explanation

The `Jpa2Application` class is the main entry point for the Spring Boot application. It is annotated with `@SpringBootApplication`, which is a convenience annotation that adds all of the following:
- `@Configuration`: Tags the class as a source of bean definitions for the application context.
- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
- `@ComponentScan`: Tells Spring to look for other components, configurations, and services in the `org.example.jpa2` package.

#### Main Method
The `main` method uses `SpringApplication.run` to launch the application.

```java
public static void main(String[] args) {
    SpringApplication.run(Jpa2Application.class, args);
}
```

#### CommandLineRunner Bean
The `CommandLineRunner` bean is used to execute code after the Spring Boot application has started. It is defined as a `@Bean` and is executed with the `start` method.

```java
@Bean
CommandLineRunner start(IHospitalService hospitalService) {
    return args -> {
        // Code to initialize the database with sample data
    };
}
```

#### Sample Data Initialization
Within the `CommandLineRunner`, sample data is created and saved using the `IHospitalService` methods:
- **Doctors (`Medcin`)**: A stream of names is used to create and save `Medcin` entities.
- **Patients (`Patient`)**: A stream of names is used to create and save `Patient` entities.
- **Appointments (`RendezVous`)**: An appointment is created and saved, associating a `Patient` and a `Medcin`.
- **Consultations (`Consultation`)**: A consultation is created and saved, associating it with an appointment.

```java
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
Medcin medcin = hospitalService.findMedcinById(1L);

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
```

This setup ensures that the application has some initial data to work with when it starts.

### Web Explanation

#### `PatientRestController`
The `PatientRestController` class is a Spring REST controller that handles HTTP requests related to `Patient` entities. It is annotated with `@RestController`, which makes it a web controller capable of handling RESTful web services.

```java
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
```

- **Annotations:**
    - `@RestController`: Indicates that this class is a REST controller.
    - `@Autowired`: Injects the `PatientRepository` dependency.

- **Methods:**
    - `list()`: Handles GET requests to the `/Patients` endpoint and returns a list of all `Patient` entities from the database.

## Conclusion
This project demonstrates the use of JPA2 in a Spring Boot application to manage entities, repositories, services, and REST controllers. By following the project structure and code explanation, you can understand how to create a simple JPA2 application using Spring Boot.
