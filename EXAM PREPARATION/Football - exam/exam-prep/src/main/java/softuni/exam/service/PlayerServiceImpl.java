package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.GlobalConstants.PLAYER_FILE_PATH;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PlayerRepository playerRepository;
    private final PictureService pictureService;
    private final TeamService teamService;

    public PlayerServiceImpl(Gson gson,
                             ModelMapper modelMapper,
                             ValidatorUtil validatorUtil,
                             PlayerRepository playerRepository,
                             PictureService pictureService,
                             TeamService teamService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.playerRepository = playerRepository;
        this.pictureService = pictureService;
        this.teamService = teamService;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlayerSeedDto[] playerSeedDtos = this.gson.fromJson(new FileReader(PLAYER_FILE_PATH), PlayerSeedDto[].class);

        Arrays.stream(playerSeedDtos).forEach(playerSeedDto -> {
            if (getPlayerByFirstNameAndLastName(playerSeedDto.getFirstName(), playerSeedDto.getLastName()) != null) {
                sb.append("Already in DB");
                return;
            }
            if (this.validatorUtil.isValid(playerSeedDto)) {
                if (this.pictureService.getPictureByUrl(playerSeedDto.getPicture().getUrl()) != null) {
                    if (this.teamService.getTeamByNam(playerSeedDto.getTeam().getName()) != null) {
                        Player player = this.modelMapper.map(playerSeedDto, Player.class);

                        Picture picture = this.pictureService.getPictureByUrl(playerSeedDto.getPicture().getUrl());
                        Team team = this.teamService.getTeamByNam(playerSeedDto.getTeam().getName());

                        player.setPicture(picture);
                        player.setTeam(team);

                        this.playerRepository.saveAndFlush(player);
                        sb.append(String.format("Successfully imported player: %s %s", player.getFirstName(), player.getLastName()));

                    } else {
                        sb.append("Team doesn't exist");
                    }
                } else {
                    sb.append("Picture doesn't exist");
                }
            } else {
                sb.append("Invalid player");
            }
            sb.append(System.lineSeparator());

        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public Player getPlayerByFirstNameAndLastName(String fName, String lName) {
        return this.playerRepository.findFirstByFirstNameAndLastName(fName, lName);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder stringBuilder = new StringBuilder();
        this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000)).stream().forEach(player -> {
            stringBuilder.append(String.format("Player name: %s %s \n" +
                            "Number: %d\n" +
                            "Salary: %.2f\n" +
                            "Team: %s\n"
                    , player.getFirstName(), player.getLastName()
                    , player.getNumber()
                    , player.getSalary()
                    , player.getTeam().getName()));
        });
        return stringBuilder.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        this.playerRepository.findAllByTeamName("North Hub").stream().forEach(player -> {
            sb.append(String.format("Team: %s\n" +
                            "\tPlayer name: %s %s - %s\n" +
                            "\tNumber: %d\n"
                    , player.getTeam().getName()
                    , player.getFirstName(), player.getLastName(), player.getPosition()
                    , player.getNumber()));

        });
        System.out.println();
        return sb.toString().trim();


    }
}
