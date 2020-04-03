package com.example.game_store.domain.dtos.orders_dto;

import com.example.game_store.domain.entities.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddItemDto {

    @NotNull
    private long id;
}

