package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.TEAM_FILE_PATH;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final TeamRepository teamRepository;
    private final PictureService pictureService;

    public TeamServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, TeamRepository teamRepository, PictureService pictureService) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
    }


    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TeamSeedRootDto teamSeedRootDto = this.xmlParser.unmarshalFromFile(TEAM_FILE_PATH, TeamSeedRootDto.class);

        teamSeedRootDto.getTeamSeedDtos().stream().forEach(teamSeedDto -> {
            if (getTeamByNam(teamSeedDto.getName()) != null) {
                sb.append("Already in DB");
            }

            if (this.validatorUtil.isValid(teamSeedDto)) {

                if (this.pictureService.getPictureByUrl(teamSeedDto.getPicture().getUrl()) != null){
                    Team team = this.modelMapper.map(teamSeedDto,Team.class);

                    Picture picture = this.pictureService.getPictureByUrl(teamSeedDto.getPicture().getUrl());

                    team.setPicture(picture);

                    this.teamRepository.saveAndFlush(team);
                    sb.append(String.format("Successfully imported - %s",team.getName()));
                }else {
                    sb.append("Picture doesn't exist");
                }
            }else {
                sb.append("Invalid team");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(TEAM_FILE_PATH));
    }

    @Override
    public Team getTeamByNam(String name) {
        return this.teamRepository.findFirstByName(name);
    }

}
