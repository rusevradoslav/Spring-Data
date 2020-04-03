package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.RacerRepository;
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
import java.util.List;
import java.util.stream.Collectors;

import static mostwanted.common.Constants.RACER_FILE_PATH;

@Service
@Transactional
public class RacerServiceImpl implements RacerService {

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final TownService townService;

    public RacerServiceImpl(ModelMapper modelMapper, Gson gson, RacerRepository racerRepository, FileUtil fileUtil, ValidationUtil validationUtil, TownService townService) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }

    @Override
    public Boolean racersAreImported() {
        //TODO: Implement me
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFileContinent(RACER_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(new FileReader(RACER_FILE_PATH), RacerImportDto[].class);

        System.out.println();

        Arrays.stream(racerImportDtos).forEach(racerImportDto -> {
            if (getRacerByName(racerImportDto.getName())) {
                sb.append("Error: Duplicate Data!").append(System.lineSeparator());
                return;
            }

            if (validationUtil.isValid(racerImportDto)) {
                if (this.townService.getTownByName(racerImportDto.getHomeTown()) != null) {
                    Racer racer = this.modelMapper.map(racerImportDto, Racer.class);

                    racer.setHomeTown(this.townService.getTownByName(racerImportDto.getHomeTown()));

                    this.racerRepository.saveAndFlush(racer);
                    sb.append(String.format("Successfully imported Racer - %s.", racer.getName()));

                } else {
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
    public boolean getRacerByName(String name) {
        return this.racerRepository.findFirstByName(name) != null;
    }

    @Override
    public Racer getRacerByFullName(String racerName) {
        return this.racerRepository.findFirstByName(racerName);
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();
        List<Racer> racers = this.racerRepository.findAll().stream().filter(racer -> racer.getAge() == null).sorted((f, s) -> {

                    int result = s.getCars().size() - f.getCars().size();

                    if (result == 0) {
                        result = f.getName().compareTo(s.getName());
                    }
                    return result;
                }

        ).collect(Collectors.toList());

        racers.stream().forEach(racer -> {
            sb.append(String.format("Name: %s\n", racer.getName()));
            sb.append("Cars:\n");
            racer.getCars().stream().forEach(car -> {
                sb.append(String.format("\t%s %s %d\n", car.getBrand(), car.getModel(), car.getYearOfProduction()));
            });
        });
        return sb.toString();
    }
}
