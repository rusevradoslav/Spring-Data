package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.CardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.EMPLOYEE_CARDS_FILE_PATH;

@Service
@Transactional

public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeCardRepository cardRepository;

    public EmployeeCardServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, EmployeeCardRepository cardRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.cardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        CardSeedDto[] cardSeedDtos = this.gson.fromJson(new FileReader(EMPLOYEE_CARDS_FILE_PATH), CardSeedDto[].class);

        Arrays.stream(cardSeedDtos).forEach(cardSeedDto -> {
            if (getFirstByNumber(cardSeedDto.getNumber()) != null) {
                sb.append("Already in DB").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(cardSeedDto)) {

                EmployeeCard employeeCard = this.modelMapper.map(cardSeedDto, EmployeeCard.class);

                this.cardRepository.saveAndFlush(employeeCard);

                sb.append(String.format("Successfully imported Employee Card %s.", employeeCard.getNumber()));
            }else {

                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString().trim();
    }

    @Override
    public EmployeeCard getFirstByNumber(String number) {
        return this.cardRepository.findFirstByNumber(number);
    }
}
