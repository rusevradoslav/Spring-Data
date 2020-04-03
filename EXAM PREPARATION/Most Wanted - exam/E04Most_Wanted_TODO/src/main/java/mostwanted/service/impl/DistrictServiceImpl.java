package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.DistrictService;
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

import static mostwanted.common.Constants.DISTRICT_FILE_PATH;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final TownService townService;

    public DistrictServiceImpl(ModelMapper modelMapper, Gson gson, DistrictRepository districtRepository, FileUtil fileUtil, ValidationUtil validationUtil, TownService townService) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFileContinent(DISTRICT_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(new FileReader(DISTRICT_FILE_PATH), DistrictImportDto[].class);

        Arrays.stream(districtImportDtos).forEach(districtImportDto -> {
            if (detDistrictByName(districtImportDto.getName())) {
                sb.append("Error: Duplicate Data!").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(districtImportDto)) {
                if (this.townService.getTownByName(districtImportDto.getTownName()) != null) {
                    District district = this.modelMapper.map(districtImportDto, District.class);

                    district.setTown(this.townService.getTownByName(districtImportDto.getTownName()));

                    this.districtRepository.saveAndFlush(district);
                    sb.append(String.format("Successfully imported District - %s.", district.getName()));

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

    private boolean detDistrictByName(String name) {
        return this.districtRepository.findFirstByName(name) != null;
    }
}
