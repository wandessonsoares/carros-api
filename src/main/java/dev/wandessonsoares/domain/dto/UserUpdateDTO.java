package dev.wandessonsoares.domain.dto;

import java.util.List;

public record UserUpdateDTO(String firstName, String lastName, String email, String birthDay, String login,
                            String password, String phone, List<CarDTO> cars) {
}
