package com.example.game_store.services;

import com.example.game_store.domain.dtos.game_dtos.GameAddDto;
import com.example.game_store.domain.dtos.game_dtos.GameEditDto;
import com.example.game_store.domain.entities.Game;
import org.springframework.stereotype.Service;

public interface GameService {

    void addGame(GameAddDto gameAddDto);

    void editaGame(GameEditDto gameEditDto);

    void deleteGame(long id);

    void viewAllGames();

    void viewGameDetails(String title);

    long getGameId(String title);

    Game getGameById(long id);
}
