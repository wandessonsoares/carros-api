package dev.wandessonsoares.repository;

import dev.wandessonsoares.domain.Car;
import dev.wandessonsoares.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query( "select c from CAR c join USUARIO u where c.user.id = :userId and c.id = :id" )
    Optional<Car> findById(@Param("id") Long id, @Param("userId") Long userId);
    void deleteById(Long id);
    @Query( "select c from CAR c join USUARIO u ON c.user.id = :id" )
    List<Car> findByUserId(@Param("id") Long id);
}
