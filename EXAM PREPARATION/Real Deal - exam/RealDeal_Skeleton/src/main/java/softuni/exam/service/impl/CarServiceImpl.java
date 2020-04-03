package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.GlobalConstants.*;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PictureRepository pictureRepository;

    public CarServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CarRepository carRepository, PictureRepository pictureRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Paths.get(CAR_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        CarSeedDto[] carSeedDtos = this.gson.fromJson(new FileReader(CAR_FILE_PATH), CarSeedDto[].class);

        Arrays.stream(carSeedDtos).forEach(carSeedDto -> {
            if (getCarByMakeModelAndKm(carSeedDto.getMake(), carSeedDto.getModel(), carSeedDto.getKilometers())) {
                sb.append("Car is already in DB").append(System.lineSeparator());
                return;

            }
            if (validationUtil.isValid(carSeedDto)) {
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                carRepository.saveAndFlush(car);
                sb.append(String.format("Successfully imported car - %s - %s", car.getMake(), car.getModel()));

            } else {
                sb.append("Invalid car");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        List<Car> cars = this.carRepository.findAll().stream().sorted((f, s) -> {
            int result = s.getPictures().size() - f.getPictures().size();

            if (result == 0) {
                result = s.getMake().compareTo(f.getMake());
            }
            return result;
        }).collect(Collectors.toList());

        cars.stream().forEach(car -> {
            sb.append(String.format("Car make - %s, model - %s\n" +
                            "\tKilometers - %d\n" +
                            "\tRegistered on - %s\n" +
                            "\tNumber of pictures - %d\n",
                    car.getMake(), car.getModel()
                    , car.getKilometers()
                    , car.getRegisteredOn()
                    , car.getPictures().size()));
        });

        return sb.toString().trim();
    }

    private boolean getCarByMakeModelAndKm(String make, String model, Long kilometers) {
        return this.carRepository.findFirstByMakeAndModelAndKilometers(make, model, kilometers) != null;
    }


    @Override
    public Car findCarById(long id) {
        return carRepository.findFirstById(id);
    }
}
