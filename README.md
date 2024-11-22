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
