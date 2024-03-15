package dev.wandessonsoares.utils;

import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.dto.CarDTO;
import dev.wandessonsoares.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConvertUserDTO {

    @Autowired
    ConvertCarDTO convertCarDTO;

    public UserDTO convert (User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDay(user.getBirthDay());
        userDTO.setPhone(user.getPhone());

        ArrayList<CarDTO> carsDTO = new ArrayList<>();
        user.getCars().forEach(car -> {
            carsDTO.add(convertCarDTO.convert(car));
        });

        userDTO.setCars(carsDTO);

        return userDTO;
    }
}
