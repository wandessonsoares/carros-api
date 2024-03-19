package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.dto.UserUpdateDTO;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.domain.dto.UserDTO;
import dev.wandessonsoares.services.CarService;
import dev.wandessonsoares.services.UserService;
import dev.wandessonsoares.utils.ConverUserUpdateDTO;
import dev.wandessonsoares.utils.ConvertJSONObject;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    ConverUserUpdateDTO converUserUpdateDTO;
    @Autowired
    ConvertJSONObject convertJSONObject;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<UserDTO> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.findUserDTOById(id)
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
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
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(record -> {
                    userService.deleteUserById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserUpdateDTO updateUser) throws JdbcSQLIntegrityConstraintViolationException{
        try{
            User user = converUserUpdateDTO.convert(updateUser, id);
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), "User not found");

            return userService.findUserById(id)
                    .map(record -> {
                        userService.updateUser(user, record.getId());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    }).orElse(
                            ResponseEntity.badRequest().body(json.toString())
                    );
        } catch (Exception e) {
            String msg = "";
            if (e.getMessage().contains("EMAIL")){
                msg = "Email already exists";
            } else if (e.getMessage().contains("LOGIN")) {
                msg = "Login already exists";
            } else if (e.getMessage().contains("not-null")) {
                msg = "Missing fields";
            } else {
                msg = e.getMessage();
            }
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
        }
    }
}
