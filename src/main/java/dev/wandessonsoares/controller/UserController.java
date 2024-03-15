package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.domain.user.UserRole;
import dev.wandessonsoares.dto.UserDTO;
import dev.wandessonsoares.services.CarService;
import dev.wandessonsoares.services.UserService;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CarService carService;
    @Autowired
    ConvertUserDTO convertUserDTO;

    @GetMapping("")
    public List<UserDTO> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.findUserDTOById(id)
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if (userService.findUserByLogin(user.getLogin()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login ja cadastrado.");
        } else {
            userService.saveNewUser(user);
            user.getCars().forEach(car -> {
                carService.updateCar(car, car.getId(), user);
            });
            UserDTO userDTO = convertUserDTO.convert(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(record -> {
                    userService.deleteUserById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User updateUser){
        return userService.findUserById(id)
                .map(record -> {
                    userService.updateUser(updateUser, record.getId());
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
