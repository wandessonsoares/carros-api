package dev.wandessonsoares.domain.dto;

import java.util.List;

public record UserDTO(long id, String firstName, String lastName, String email, String birthDay,
                      String phone, List<CarDTO> cars
) {
}
