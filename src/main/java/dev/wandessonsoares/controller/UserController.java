package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.domain.user.UserRole;
import dev.wandessonsoares.dto.UserDTO;
import dev.wandessonsoares.services.CarService;
import dev.wandessonsoares.services.UserService;
import dev.wandessonsoares.utils.ConvertJSONObject;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.json.JSONObject;
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
    @Autowired
    ConvertJSONObject convertJSONObject;

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
    public ResponseEntity<?> saveUser(@RequestBody User user) throws JdbcSQLIntegrityConstraintViolationException {
        try {
            userService.saveNewUser(user);
            user.getCars().forEach(car -> {
                carService.updateCar(car, car.getId(), user);
            });
            UserDTO userDTO = convertUserDTO.convert(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (Exception e) {
            String msg = "";
            if (e.getMessage().contains("EMAIL")){
                msg = "Email already exists";
            } else if (e.getMessage().contains("LOGIN")) {
                msg = "Login already exists";
            } else if (e.getMessage().contains("not-null")) {
                msg = "Missing fields";
            }
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
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
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User updateUser) throws JdbcSQLIntegrityConstraintViolationException{
        try{
            return userService.findUserById(id)
                    .map(record -> {
                        userService.updateUser(updateUser, record.getId());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            String msg = "";
            if (e.getMessage().contains("EMAIL")){
                msg = "Email already exists";
            } else if (e.getMessage().contains("LOGIN")) {
                msg = "Login already exists";
            } else if (e.getMessage().contains("not-null")) {
                msg = "Missing fields";
            }
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
        }
    }
}
