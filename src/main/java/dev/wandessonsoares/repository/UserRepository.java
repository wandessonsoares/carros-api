package dev.wandessonsoares.repository;

import dev.wandessonsoares.domain.dto.UserDTO;
import dev.wandessonsoares.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);
    @Query( "select u from USUARIO u join CAR c ON u.id = c.user.id AND u.login = :login")
    Optional<User> findByLoginData(@Param("login") String login);
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
