package dev.wandessonsoares.domain.dto;

public record UserInfoDTO(UserDTO userDTO, String createdAt, String lastLogin) {
}
