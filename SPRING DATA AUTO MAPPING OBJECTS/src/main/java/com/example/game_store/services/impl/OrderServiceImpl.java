package com.example.game_store.services.impl;

import com.example.game_store.domain.dtos.orders_dto.OrderAddItemDto;
import com.example.game_store.domain.entities.Game;
import com.example.game_store.domain.entities.Order;
import com.example.game_store.repositories.GameRepository;
import com.example.game_store.repositories.OrderRepository;
import com.example.game_store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private GameRepository gameRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addItem(OrderAddItemDto orderAddItemDto, Game game) {
        Order order = this.modelMapper.map(orderAddItemDto, Order.class);
        order.getGames().add(game);
        this.orderRepository.saveAndFlush(order);
    }
}
