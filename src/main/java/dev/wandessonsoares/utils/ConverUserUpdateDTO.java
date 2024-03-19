package dev.wandessonsoares.utils;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.dto.UserUpdateDTO;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class ConverUserUpdateDTO {

    @Autowired
    CarService carService;

    public User convert (UserUpdateDTO userUpdateDTO, long id) throws ParseException {
        User user = new User();
        user.setId(id);
        user.setFirstName(userUpdateDTO.firstName());
        user.setLastName(userUpdateDTO.lastName());
        user.setEmail(userUpdateDTO.email());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date birthDayFormated = formatter.parse(userUpdateDTO.birthDay());

        user.setBirthDay(birthDayFormated);
        user.setLogin(userUpdateDTO.login());
        user.setPassword(userUpdateDTO.password());
        user.setPhone(userUpdateDTO.phone());

        List<Car> cars = new ArrayList<>();
        userUpdateDTO.cars().forEach(updateCar -> {
            Optional<Car> car = carService.findByLicensePlate(updateCar.licencePlate());
            if (car.isPresent()){
                cars.add(car.get());
            }
        });

        user.setCars(cars);

        return user;
    }
}
