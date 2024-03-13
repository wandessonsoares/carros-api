package dev.wandessonsoares.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDay;
    private String login;
    private String password;
    private String phone;
    @OneToMany(mappedBy = "user", targetEntity = Car.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ArrayList<Car> cars;

}
