package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.GloblalConstants;
import softuni.exam.models.dtos.TownSeedDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.GloblalConstants.TOWN_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    public TownServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, TownRepository townRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TownSeedDto[] townSeedDtos = this.gson.fromJson(new FileReader(TOWN_FILE_PATH), TownSeedDto[].class);

        System.out.println();
        Arrays.stream(townSeedDtos).forEach(townSeedDto -> {
            if (getTownByName(townSeedDto.getName())) {
                sb.append("Already exist").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(townSeedDto)) {
                Town town = new Town();
                town.setName(townSeedDto.getName());
                town.setGuide(townSeedDto.getGuide());
                town.setPopulation(townSeedDto.getPopulation());

                this.townRepository.saveAndFlush(town);
                sb.append(String.format("Successfully imported Town %s - %d",town.getName(),town.getPopulation()));

            } else {
                sb.append("Invalid Town");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString().trim();
    }

    private boolean getTownByName(String name) {
        return this.townRepository.findFirstByName(name) != null;
    }
}
