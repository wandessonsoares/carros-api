package dev.wandessonsoares.repository;

import dev.wandessonsoares.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findById(Long id);
    void deleteById(Long id);
}
