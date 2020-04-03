package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.PassengerSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.GloblalConstants.PASSENGER_FILE_PATH;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, TownRepository townRepository, PassengerRepository passengerRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();
        PassengerSeedDto[] passengerSeedDtos = this.gson.fromJson(new FileReader(PASSENGER_FILE_PATH), PassengerSeedDto[].class);

        Arrays.stream(passengerSeedDtos).forEach(passengerSeedDto -> {
            if (getPassengerByEmail(passengerSeedDto.getEmail())) {
                sb.append("Already in DB").append(System.lineSeparator());
            }
            if (validationUtil.isValid(passengerSeedDto)) {
                if (passengerSeedDto.getEmail().contains("@") && passengerSeedDto.getEmail().contains(".")) {
                    Passenger passenger = this.modelMapper.map(passengerSeedDto, Passenger.class);
                    Town town = this.townRepository.findFirstByName(passengerSeedDto.getTown());
                    passenger.setTown(town);
                    town.getPassengers().add(passenger);
                    passengerRepository.saveAndFlush(passenger);
                    sb.append(String.format("Successfully imported Passenger %s - %s", passenger.getLastName(), passenger.getEmail()));
                } else {
                    sb.append("Invalid Passenger").append(System.lineSeparator());
                    return;
                }
            } else {
                sb.append("Invalid Passenger!");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    private boolean getPassengerByEmail(String email) {
        return this.passengerRepository.findFirstByEmail(email) != null;
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Passenger> passengers = this.passengerRepository.getAllByOrderByTicketsCountThenByEmailAsc();

        passengers.stream().forEach(passenger -> {
            stringBuilder.append(String.format(
                    "Passenger %s  %s\n" +
                            "\tEmail - %s\n" +
                            "\tPhone - %s\n" +
                            "\tNumber of tickets - %d\n"
                    , passenger.getFirstName(), passenger.getLastName()
                    , passenger.getEmail()
                    , passenger.getPhoneNumber()
                    , passenger.getTickets().size()));
        });
        return stringBuilder.toString();
    }
}
