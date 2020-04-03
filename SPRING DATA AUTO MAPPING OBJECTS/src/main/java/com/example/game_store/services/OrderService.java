package com.example.game_store.services;

import com.example.game_store.domain.dtos.orders_dto.OrderAddItemDto;
import com.example.game_store.domain.entities.Game;

public interface OrderService {
    void addItem(OrderAddItemDto orderAddItemDto, Game game);
}
