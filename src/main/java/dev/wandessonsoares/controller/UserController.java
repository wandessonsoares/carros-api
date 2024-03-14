package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.User;
import dev.wandessonsoares.dto.UserDTO;
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
    ConvertUserDTO convertUserDTO;

    @GetMapping("")
    public List<UserDTO> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.findUserById(id)
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user){
        userService.saveNewUser(user);
        UserDTO userDTO = convertUserDTO.convert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(record -> {
                    userService.deleteUserById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}