package dev.wandessonsoares.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="USUARIO")
@Table(name="USUARIO")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Data
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthDay;
    private String login;
    private String password;
    private String phone;
    @OneToMany(mappedBy="user", targetEntity= Car.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Car> cars;
}