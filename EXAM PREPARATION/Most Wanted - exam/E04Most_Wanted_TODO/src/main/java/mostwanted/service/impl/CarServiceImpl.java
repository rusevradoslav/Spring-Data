package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.DistrictRepository;
import mostwanted.service.CarService;
import mostwanted.service.RacerService;
import mostwanted.service.TownService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static mostwanted.common.Constants.CAR_FILE_PATH;
import static mostwanted.common.Constants.RACER_FILE_PATH;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final CarRepository carRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final RacerService racerService;

    public CarServiceImpl(ModelMapper modelMapper, Gson gson, CarRepository carRepository, FileUtil fileUtil, ValidationUtil validationUtil, RacerService racerService) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.carRepository = carRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.racerService = racerService;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFileContinent(CAR_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(new FileReader(CAR_FILE_PATH), CarImportDto[].class);

        Arrays.stream(carImportDtos).forEach(carImportDto -> {
            if (validationUtil.isValid(carImportDto)) {
                if (racerService.getRacerByFullName(carImportDto.getRacerName()) != null) {
                    Car car = this.modelMapper.map(carImportDto, Car.class);
                    car.setRacer(racerService.getRacerByFullName(carImportDto.getRacerName()));

                    Racer racer = racerService.getRacerByFullName(carImportDto.getRacerName());

                    racer.getCars().add(car);

                    carRepository.saveAndFlush(car);

                    sb.append(String.format("Successfully imported Car - %s %s @ %d.",car.getBrand(), car.getModel(),car.getYearOfProduction()));
                }else {
                    sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                    return;
                }
            } else {
                sb.append("Error: Incorrect Data!");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public Car getCarById(Long car) {
        return this.carRepository.findFirstById(car);
    }
}
