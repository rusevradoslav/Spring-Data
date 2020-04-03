package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.service.TownService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static mostwanted.common.Constants.TOWN_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {
    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(ModelMapper modelMapper, Gson gson, TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {

        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(new FileReader(TOWNS_JSON_FILE_PATH), TownImportDto[].class);

        Arrays.stream(townImportDtos).forEach(townImportDto -> {
            if (getTownName(townImportDto.getName())) {
                sb.append("Error: Duplicate Data!").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(townImportDto)) {
                Town town = this.modelMapper.map(townImportDto, Town.class);
                townRepository.saveAndFlush(town);
                sb.append(String.format("Successfully imported Town - %s.", town.getName()));
            } else {
                sb.append("Error: Incorrect Data!");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    private boolean getTownName(String name) {
        return this.townRepository.findFirstByName(name) != null;
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Town> towns = this.townRepository.findAll().stream().sorted((f, s) -> {
            int result = s.getRacers().size() - f.getRacers().size();

            if (result == 0) {
                result = f.getName().compareTo(s.getName());
            }
            return result;
        }).collect(Collectors.toList());

        towns.stream().forEach(town -> {
            stringBuilder.append(String.format("Name: %s\n" +
                    "Racers:%d\n",town.getName(),town.getRacers().size()));
        });


        return stringBuilder.toString();
    }

    @Override
    public Town getTownByName(String townName) {
        return this.townRepository.findFirstByName(townName);
    }
}
