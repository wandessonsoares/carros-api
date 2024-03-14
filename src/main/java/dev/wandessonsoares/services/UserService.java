package dev.wandessonsoares.services;

import dev.wandessonsoares.domain.Car;
import dev.wandessonsoares.domain.User;
import dev.wandessonsoares.dto.UserDTO;
import dev.wandessonsoares.repository.CarRepository;
import dev.wandessonsoares.repository.UserRepository;
import dev.wandessonsoares.utils.ConvertUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<UserDTO> findUserDTOById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            UserDTO userDTO = convertUserDTO.convert(user.get());
            return Optional.ofNullable(userDTO);
        }
        return null;
    }

    public Optional<User> findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return Optional.of(user.get());
        }
        return null;
    }

    public User saveNewUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User updateUser, Long id){
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
            user.setCars(updateUser.getCars());
            userRepository.save(user);
        }
        return null;
    }

    public void deleteUserById(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

}
