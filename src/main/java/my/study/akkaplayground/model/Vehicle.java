package my.study.akkaplayground.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;
    private String registrationNumber;
    private String registrationCountry;
    private LocalDate registrationDate;
    private LocalDate expirationDate;
    @ManyToOne
    @JoinColumn(name="person_id")
    private Person owner;

}
