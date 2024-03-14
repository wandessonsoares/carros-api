package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.Car;
import dev.wandessonsoares.domain.User;
import dev.wandessonsoares.dto.CarDTO;
import dev.wandessonsoares.dto.UserDTO;
import dev.wandessonsoares.services.CarService;
import dev.wandessonsoares.services.UserService;
import dev.wandessonsoares.utils.ConvertCarDTO;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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

    @GetMapping("")
    public ResponseEntity<List<?>> getCarsByUser(){
        Optional <User> user = userService.findUserById(Long.parseLong("1"));
        if (user.isPresent()){
            List<CarDTO> carsDTO =  carService.findAllCarsByUser(user.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(carsDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id){
        Optional <User> user = userService.findUserById(Long.parseLong("1"));
        return carService.findCarDTOById(id, user.get().getId())
                .map(record -> {
                    return ResponseEntity.status(HttpStatus.OK).body(record);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> saveCar(@RequestBody Car car){
        Optional <User> user = userService.findUserById(Long.parseLong("1"));
        if(user.isPresent()){
            carService.saveNewCar(car, user.get());
        }
        CarDTO carDTO = convertCarDTO.convert(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {
        Optional <User> user = userService.findUserById(Long.parseLong("1"));
        return carService.findCarById(id, user.get().getId())
                .map(record -> {
                    carService.deleteCarById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarById(@PathVariable Long id, @RequestBody Car updateCar){
        Optional <User> user = userService.findUserById(Long.parseLong("1"));
        return carService.findCarById(id, user.get().getId())
                .map(record -> {
                    carService.updateCar(updateCar, id, user.get());
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
