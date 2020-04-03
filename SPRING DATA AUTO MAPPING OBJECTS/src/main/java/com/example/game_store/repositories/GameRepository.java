package com.example.game_store.repositories;

import com.example.game_store.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findById(long id);

    Game findGameByTitle(String title);



}
