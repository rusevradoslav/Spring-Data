package mostwanted.service.impl;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.service.CarService;
import mostwanted.service.RaceEntryService;
import mostwanted.service.RacerService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static mostwanted.common.Constants.RACE_ENTRIES_FILE_PATH;

@Service
@Transactional
public class RaceEntryServiceImpl implements RaceEntryService {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final RaceEntryRepository raceEntryRepository;
    private final CarService carService;
    private final RacerService racerService;

    public RaceEntryServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil, RaceEntryRepository raceEntryRepository, CarService carService, RacerService racerService) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.raceEntryRepository = raceEntryRepository;
        this.carService = carService;
        this.racerService = racerService;
    }


    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFileContinent(RACE_ENTRIES_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser.unmarshalFromFile(RACE_ENTRIES_FILE_PATH, RaceEntryImportRootDto.class);

        raceEntryImportRootDto.getRacers().stream().forEach(raceEntryImportDto -> {
            if (this.validationUtil.isValid(raceEntryImportDto)) {
                if (carService.getCarById(raceEntryImportDto.getCar()) != null) {
                    if (racerService.getRacerByFullName(raceEntryImportDto.getRacer()) != null) {

                        RaceEntry raceEntry = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
                        raceEntry.setCar(carService.getCarById(raceEntryImportDto.getCar()));
                        raceEntry.setRacer(racerService.getRacerByFullName(raceEntryImportDto.getRacer()));

                        raceEntryRepository.saveAndFlush(raceEntry);
                        sb.append(String.format("Successfully imported RaceEntry - %d.", raceEntry.getId()));

                    } else {
                        sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                        return;
                    }
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
    public RaceEntry getEntryById(Long id) {
        return this.raceEntryRepository.findFirstById(id);
    }
}
