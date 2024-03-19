package dev.wandessonsoares.services;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.dto.UserInfoDTO;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.domain.user.UserRole;
import dev.wandessonsoares.domain.dto.UserDTO;
import dev.wandessonsoares.repository.UserRepository;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarService carService;

    @Autowired
    ConvertUserDTO convertUserDTO;

    public List<UserDTO> findAllUsers(){
        List<User> users = userRepository.findAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> {
            userDTOS.add(convertUserDTO.convert(user));
        });
        return userDTOS;
    }

    public Optional<?> findUserDTOById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            UserDTO userDTO = convertUserDTO.convert(user.get());
            return Optional.ofNullable(userDTO);
        }
        return user;
    }

    public Optional<User> findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public UserDetails findUserByLogin(String login){
        UserDetails user = userRepository.findByLogin(login);
        return user;
    }

    public Optional<UserInfoDTO> findUserByLoginDataDTO(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> userLogged = userRepository.findByLoginData(userDetails.getUsername());
        UserDTO userDTO = convertUserDTO.convert(userLogged.get());

        UserInfoDTO userInfoDTO =
                new UserInfoDTO(userDTO, userLogged.get().getCreatedAt(), userLogged.get().getLastLogin());

        return Optional.ofNullable(userInfoDTO);
    }

    public Optional<User> findUserByLoginData(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> userLogged = userRepository.findByLoginData(userDetails.getUsername());

        return userLogged;
    }

    public User saveNewUser(User user){
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);
        user.setCreatedAt(Calendar.getInstance().getTime().toString());

        return userRepository.save(user);
    }

    public Optional<User> updateUser(User updateUser, Long id){
        Optional<User> userSaved = userRepository.findById(id);
        if (userSaved.isPresent()){
            User user = userSaved.get();
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            user.setEmail(updateUser.getEmail());
            user.setBirthDay(updateUser.getBirthDay());
            user.setLogin(updateUser.getLogin());
            user.setPassword(updateUser.getPassword());
            user.setPhone(updateUser.getPhone());

            List<Car> cars =  updateUser.getCars();
            List<Car> updatedCars = new ArrayList<>();

            cars.forEach(car -> {
                Optional<Car> carUser = carService.findByLicensePlate(car.getLicensePlate());
                if (carUser.isPresent()) {
                    Car updatedCar = carService.updateCar(car, carUser.get().getId(), user);
                    updatedCars.add(updatedCar);
                }
            });

            user.setCars(updatedCars);
            userRepository.save(user);
        }
        return userSaved;
    }

    public void deleteUserById(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

}
