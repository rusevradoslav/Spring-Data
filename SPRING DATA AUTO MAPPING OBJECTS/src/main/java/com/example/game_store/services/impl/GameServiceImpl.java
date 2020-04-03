package com.example.game_store.services.impl;

import com.example.game_store.domain.dtos.game_dtos.GameAddDto;
import com.example.game_store.domain.dtos.game_dtos.GameDto;
import com.example.game_store.domain.dtos.game_dtos.GameEditDto;
import com.example.game_store.domain.entities.Game;
import com.example.game_store.repositories.GameRepository;
import com.example.game_store.services.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private GameDto gameDto;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addGame(GameAddDto gameAddDto) {
        Game game = this.modelMapper.map(gameAddDto, Game.class);
        this.gameRepository.saveAndFlush(game);
        System.out.println();
    }

    @Override
    public void editaGame(GameEditDto gameEditDto) {
        Game game = this.gameRepository.findById(gameEditDto.getId());

        if (game == null) {
            System.out.println("This game doesn't exist.");
        } else {
            game.setPrice(gameEditDto.getPrice());
            game.setSize(gameEditDto.getSize());
            this.gameRepository.saveAndFlush(game);
            System.out.printf("Successfully edited game %s%n", game.getTitle());
        }
    }

    @Override
    public void deleteGame(long gameId) {
        Game game = this.gameRepository.findById(gameId);

        if (game == null) {
            System.out.println("This game doesn't exist.");
        } else {
            this.gameRepository.delete(game);
            System.out.printf("Successfully deleted game %s%n", game.getTitle());

        }
    }

    @Override
    public void viewAllGames() {
        this.gameRepository
                .findAll()
                .stream()
                .forEach(game -> System.out.printf("%s %.2f%n", game.getTitle(), game.getPrice()));
    }

    @Override
    public void viewGameDetails(String title) {
        Game game = this.gameRepository.findGameByTitle(title);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Title: %s%n%n", game.getTitle()))
                .append(String.format("Price: %.2f%n%n", game.getPrice()))
                .append(String.format("Description: %s%n%n", game.getDescription()))
                .append(String.format("Release date: %s%n%n", game.getRelease_date()));

        System.out.println(sb.toString());
    }

    @Override
    public long getGameId(String title) {
        Game game = gameRepository.findGameByTitle(title);

        return game.getId();
    }

    @Override
    public Game getGameById(long id) {
        return this.gameRepository.findById(id);
    }
}
