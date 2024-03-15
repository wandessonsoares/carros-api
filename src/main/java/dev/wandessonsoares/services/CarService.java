package dev.wandessonsoares.services;

import dev.wandessonsoares.domain.car.Car;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.dto.CarDTO;
import dev.wandessonsoares.repository.CarRepository;
import dev.wandessonsoares.utils.ConvertCarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    ConvertCarDTO convertCarDTO;

    public List<CarDTO> findAllDTO(Long id){
        List<Car> cars = carRepository.findByUserId(id);
        ArrayList carsDTO = new ArrayList();
        cars.forEach(car -> {
            carsDTO.add(convertCarDTO.convert(car));
        });
        return carsDTO;
    }

    public List<Car> findAll(Long id){
        return carRepository.findByUserId(id);
    }

    public List<CarDTO> findAllCarsByUser(Long id){
        List<Car> cars = carRepository.findAllById(Collections.singleton(id));
        ArrayList<CarDTO> carsDTO = new ArrayList<>();
        cars.forEach(car -> {
            carsDTO.add(convertCarDTO.convert(car));
        });
        return carsDTO;
    }

    public Optional<CarDTO> findCarDTOById(Long id, Long userId){
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()){
            CarDTO carDTO = convertCarDTO.convert(car.get());
            return Optional.ofNullable(carDTO);
        }
        return null;
    }

    public Optional<Car> findCarById(Long id, Long userId){
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()){
            return car;
        }
        return null;
    }

    public Car saveNewCar(Car car, User user){
        car.setUser(user);
        return carRepository.save(car);
    }

    public Car updateCar(Car updateCar, Long id, User user){
        Optional<Car> carSaved = carRepository.findById(id);
        if (carSaved.isPresent()){
            Car car = carSaved.get();
            car.setCarYear(updateCar.getCarYear());
            car.setLicensePlate(updateCar.getLicensePlate());
            car.setModel(updateCar.getModel());
            car.setColor(updateCar.getColor());
            car.setUser(user);
            carRepository.save(car);
        }
        return null;
    }

    public void deleteCarById(Long id){
        if (carRepository.existsById(id)){
            carRepository.deleteById(id);
        }
    }

}
