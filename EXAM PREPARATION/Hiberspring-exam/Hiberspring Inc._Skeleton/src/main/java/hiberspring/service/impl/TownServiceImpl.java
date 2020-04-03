package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.TOWNS_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        TownSeedDto[] townSeedDtos = this.gson.fromJson(new FileReader(TOWNS_FILE_PATH), TownSeedDto[].class);
        Arrays.stream(townSeedDtos).forEach(townSeedDto -> {
            if (getTownByName(townSeedDto.getName()) != null){
                sb.append("Already in DB").append(System.lineSeparator());
                return;
            }
            if (this.validationUtil.isValid(townSeedDto)){
                Town town = this.modelMapper.map(townSeedDto,Town.class);

               this.townRepository.saveAndFlush(town);
               sb.append(String.format("Successfully imported Town %s.",town.getName()));

            }else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());

        });
        return sb.toString();
    }

    @Override
    public Town getTownByName(String townName) {
        return this.townRepository.findFirstByName(townName);
    }
}
