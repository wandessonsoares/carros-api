package dev.wandessonsoares.utils;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.dto.CarDTO;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertCarDTO {

    public CarDTO convert (Car car){
        CarDTO carDTO = new CarDTO();
        carDTO.setCarYear(car.getCarYear());
        carDTO.setLicensePlate(car.getLicensePlate());
        carDTO.setModel(car.getModel());
        carDTO.setColor(car.getColor());

        return carDTO;
    }
}
