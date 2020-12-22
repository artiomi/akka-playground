package my.study.akkaplayground.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String idnp;
    private String citizenship;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy="owner")
    private Set<Vehicle> vehicles;

}
