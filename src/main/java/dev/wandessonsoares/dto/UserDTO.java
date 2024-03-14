package dev.wandessonsoares.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserDTO {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String email;
    private String birthDay;
    private String phone;
    private List<CarDTO> cars;
}