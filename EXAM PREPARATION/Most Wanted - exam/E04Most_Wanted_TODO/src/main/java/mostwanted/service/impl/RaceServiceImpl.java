package mostwanted.service.impl;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.races.EntryImportRootDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.service.*;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static mostwanted.common.Constants.RACE_FILE_PATH;

@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final RaceRepository raceRepository;
    private final RaceEntryService raceEntryService;
    private final DistrictService districtService;

    public RaceServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil, RaceRepository raceRepository, RaceEntryService raceEntryService, DistrictService districtService) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.raceRepository = raceRepository;
        this.raceEntryService = raceEntryService;
        this.districtService = districtService;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return fileUtil.readFileContinent(RACE_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        RaceImportRootDto raceImportRootDto = this.xmlParser.unmarshalFromFile(RACE_FILE_PATH, RaceImportRootDto.class);

        raceImportRootDto.getRaces().stream().forEach(raceImportDto -> {
            if (validationUtil.isValid(raceImportDto)) {
                Race race = this.modelMapper.map(raceImportDto, Race.class);

                List<RaceEntry> raceEntries = new ArrayList<>();
                race.getEntries().stream().forEach(raceEntry -> {
                    raceEntries.add(raceEntryService.getEntryById(raceEntry.getId()));
                });

                race.setEntries(raceEntries);
                raceRepository.saveAndFlush(race);
                sb.append(String.format("Successfully imported Race - %d.", race.getId()));
            } else {
                sb.append("Error: Incorrect Data!");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }
}