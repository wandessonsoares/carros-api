package dev.wandessonsoares.dto;

import java.util.Date;
import java.util.List;

public record UserDTO(String firstName, String lastName, String email, String birthDay,
                      String phone, List<CarDTO> cars
) {
}
