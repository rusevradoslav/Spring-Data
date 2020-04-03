package mostwanted.service;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.Query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TownService {

    Boolean townsAreImported();

    String readTownsJsonFile() throws IOException;

    String importTowns(String townsFileContent) throws FileNotFoundException;

    String exportRacingTowns();

    Town getTownByName(String townName);

}
