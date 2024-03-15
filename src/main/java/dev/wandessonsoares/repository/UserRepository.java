package dev.wandessonsoares.repository;

import dev.wandessonsoares.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
