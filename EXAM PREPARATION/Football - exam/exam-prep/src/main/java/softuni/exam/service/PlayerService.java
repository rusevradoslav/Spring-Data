package softuni.exam.service;


import softuni.exam.domain.entities.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PlayerService {
    String importPlayers() throws FileNotFoundException;

    boolean areImported();

    String readPlayersJsonFile() throws IOException;

    Player getPlayerByFirstNameAndLastName(String fName, String lName);

    String exportPlayersInATeam();

    public String exportPlayersWhereSalaryBiggerThan();
}
