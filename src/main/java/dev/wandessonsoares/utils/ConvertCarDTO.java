package dev.wandessonsoares.utils;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.dto.CarDTO;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertCarDTO {

    public CarDTO convert (Car car){
        CarDTO carDTO = new CarDTO(
                car.getCarYear(),
                car.getLicensePlate(),
                car.getModel(),
                car.getColor()
        );


        return carDTO;
    }
}
