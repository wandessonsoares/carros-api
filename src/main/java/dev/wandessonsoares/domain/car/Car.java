package dev.wandessonsoares.domain.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.wandessonsoares.domain.car.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name="CAR")
@Table(name="CAR")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String carYear;
    @Getter @Setter
    private String licensePlate;
    @Getter @Setter
    private String model;
    @Getter @Setter
    private String color;

    @Getter @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private User user;
}