package dev.wandessonsoares.domain.car;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.wandessonsoares.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name="CAR")
@Table(name="CAR")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Car {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private long id;
    @Column(nullable = false)
    private int carYear;
    @Column(nullable = false, unique = true)
    private String licensePlate;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String color;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private User user;
}