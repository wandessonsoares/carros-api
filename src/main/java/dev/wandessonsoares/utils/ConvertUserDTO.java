package dev.wandessonsoares.utils;

import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.dto.CarDTO;
import dev.wandessonsoares.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Configuration
public class ConvertUserDTO {

    @Autowired
    ConvertCarDTO convertCarDTO;

    public UserDTO convert (User user){
        ArrayList<CarDTO> carsDTO = new ArrayList<>();
        user.getCars().forEach(car -> {
            carsDTO.add(convertCarDTO.convert(car));
        });

        String birthDayFormated = new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDay());

        UserDTO userDTO = new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                birthDayFormated,
                user.getPhone(),
                carsDTO
        );

        return userDTO;
    }
}
