package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.domain.dto.CarDTO;
import dev.wandessonsoares.services.CarService;
import dev.wandessonsoares.services.UserService;
import dev.wandessonsoares.utils.ConvertCarDTO;
import dev.wandessonsoares.utils.ConvertJSONObject;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    CarService carService;
    @Autowired
    UserService userService;
    @Autowired
    ConvertCarDTO convertCarDTO;
    @Autowired
    ConvertJSONObject convertJSONObject;

    @GetMapping("")
    public ResponseEntity<List<?>> getCarsByUser(){
        Optional <User> userLogged = userService.findUserByLoginData();
        if (userLogged.isPresent()){
            List<CarDTO> carsDTO =  carService.findAllCarsByUser(userLogged.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(carsDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id){
        Optional <User> userLogged = userService.findUserByLoginData();

        return carService.findCarDTOById(id, userLogged.get().getId())
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("")
    public ResponseEntity<?> saveCar(@RequestBody Car car) throws JdbcSQLIntegrityConstraintViolationException {
        try{
            Optional <User> userLogged = userService.findUserByLoginData();
            if(userLogged.isPresent()){
                carService.saveNewCar(car, userLogged.get());
            }
            CarDTO carDTO = convertCarDTO.convert(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
        } catch (Exception e){
            String msg = "";
            if (e.getMessage().contains("LICENCE")){
                msg = "Licence plate already exists";
            } else if (e.getMessage().contains("not-null")) {
                msg = "Missing fields";
            }
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {
        Optional <User> userLogged = userService.findUserByLoginData();
        return carService.findCarById(id, userLogged.get().getId())
                .map(record -> {
                    carService.deleteCarById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarById(@PathVariable Long id, @RequestBody Car updateCar){
        try{
            Optional <User> userLogged = userService.findUserByLoginData();
            return carService.findCarById(id, userLogged.get().getId())
                    .map(record -> {
                        carService.updateCar(updateCar, id, userLogged.get());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e){
            String msg = "";
            if (e.getMessage().contains("LICENCE")){
                msg = "Licence plate already exists";
            } else if (e.getMessage().contains("not-null")) {
                msg = "Missing fields";
            }
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
        }
    }
}
