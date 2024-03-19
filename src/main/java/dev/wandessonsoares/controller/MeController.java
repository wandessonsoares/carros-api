package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.dto.CarDTO;
import dev.wandessonsoares.domain.dto.UserDTO;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/me")
public class MeController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUserInfo(){
        return userService.findUserByLoginDataDTO()
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
