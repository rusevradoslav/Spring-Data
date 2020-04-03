package com.example.game_store.controllers;

import com.example.game_store.domain.dtos.game_dtos.GameAddDto;
import com.example.game_store.domain.dtos.game_dtos.GameEditDto;
import com.example.game_store.domain.dtos.orders_dto.OrderAddItemDto;
import com.example.game_store.domain.dtos.user_dtos.UserLogInDto;
import com.example.game_store.domain.dtos.user_dtos.UserRegisterDto;
import com.example.game_store.domain.entities.Game;
import com.example.game_store.domain.entities.User;
import com.example.game_store.services.GameService;
import com.example.game_store.services.OrderService;
import com.example.game_store.services.UserService;
import com.example.game_store.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.game_store.constants.GlobalConstants.*;
import static com.example.game_store.domain.enums.Role.ADMIN;

@Component
public class AppController implements CommandLineRunner {


    private final BufferedReader bufferedReader;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;
    private User adminUser;

    @Autowired
    public AppController(BufferedReader bufferedReader, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, GameService gameService, OrderService orderService) {
        this.bufferedReader = bufferedReader;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("For instructions press 1.");
            System.out.println("Enter data:");
            String[] input = this.bufferedReader.readLine().split("\\|");
            String command = input[0];

            switch (command) {
                case REGISTER_USER_COMMAND:
                    registerUser(input);
                    break;
                case LOGIN_USER_COMMAND:
                    loginUser(input);
                    if (adminUser != null) {
                        //Hire I IMPLEMENT MANAGING GAME logic ,
                        //because only admin can AddGame , EditGame or DeleteGame.
                        //If you want to ADD , EDIT or DELETE GAME you must LogIn the ADMIN ACCOUNT (that is the first account
                        // ,that you register in the system)
                        adminUserProperties();
                    }
                    break;
                case LOGOUT_USER_COMMAND:
                    logOut();
                    break;
                case VIEW_ALL_GAMES_COMMAND:
                    viewAllGames();
                    break;
                case GAME_DETAIL_COMMAND:
                    viewGameDetail(input);
                    break;
                case VIEW_OWNED_GAMES_COMMAND:
                    //TODO(to implement this exercise i must implement exercise 5 *
                    // because only user can add item ,remove item or buy item,
                    // because the logic is that the ADMIN is owner the shop
                    // and only he can add edit or delete games );
                    break;
                case ADD_ITEM_COMMAND:
                    this.addItem(input[1]);
                    break;
                case REMOVE_ITEM_COMMAND:
                    //TODO(this is method form exercise 5*)
                    break;
                case BUY_ITEM_COMMAND:
                    //TODO(this is method form exercise 5*)
                    break;
                case INSTRUCTIONS_COMMAND:
                    this.instructions();
                    break;
                default:
                    System.out.println(INCORRECT_COMMAND_MESSAGE);
            }
        }


    }

    private void viewGameDetail(String[] input) {
        String title = input[1];
        gameService.viewGameDetails(title);
    }

    private void viewAllGames() {
        this.gameService.viewAllGames();
    }

    private void adminUserProperties() throws IOException {
        String[] input;
        String command;
        System.out.println("Logged as ADMIN");
        while (true) {
            System.out.println("Enter date:");
            boolean isLogged = true;
            input = this.bufferedReader.readLine().split("\\|");
            command = input[0];
            switch (command) {
                case ADD_GAME_COMMAND:
                    addGame(input);
                    break;
                case EDIT_GAME_COMMAND:
                    editGame(input);
                    break;
                case DELETE_GAME_COMMAND:
                    deleteGame(input);
                    break;
                case LOGOUT_USER_COMMAND:
                    this.logOut();
                    isLogged = false;
                    break;
                default:
                    System.out.println(INCORRECT_COMMAND_MESSAGE);
            }

            if (!isLogged) {
                break;
            }

        }
    }

    private void addItem(String title) {
        long orderGameId = this.gameService.getGameId(title);


        if (orderGameId != 0) {
            OrderAddItemDto orderAddItemDto = new OrderAddItemDto(orderGameId);
            Game game = this.gameService.getGameById(orderGameId);
            if (this.validationUtil.isValid(orderAddItemDto)) {
                this.orderService.addItem(orderAddItemDto, game);
                System.out.println("Successfully added item in item bag!");
            } else {
                this.validationUtil.getViolations(orderAddItemDto)
                        .stream().map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        } else {
            System.out.println("Game with this title does not exist!");
        }

    }

    private void deleteGame(String[] input) {
        long gameId = Long.parseLong(input[1]);
        gameService.deleteGame(gameId);
    }

    private void editGame(String[] input) {
        long gameId = Long.parseLong(input[1]);
        String priceString = input[2].substring(input[2].indexOf('=') + 1, input[2].length());
        BigDecimal price = new BigDecimal(priceString);
        String sizeString = input[3].substring(input[3].indexOf('=') + 1, input[3].length());
        double size = Double.parseDouble(sizeString);

        GameEditDto gameEditDto = new GameEditDto(gameId, price, size);

        if (this.validationUtil.isValid(gameEditDto)) {
            this.gameService.editaGame(gameEditDto);
        } else {
            this.validationUtil.getViolations(gameEditDto)
                    .stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }


    }

    private void addGame(String[] input) {
        String title = input[1];
        BigDecimal price = new BigDecimal(input[2]);
        double size = Double.parseDouble(input[3]);
        String trailer = input[4];
        String URL = input[5];
        String description = input[6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(input[7], formatter);

        GameAddDto gameAddDto = new GameAddDto(title, price, size, trailer, URL, description, date);


        if (this.validationUtil.isValid(gameAddDto)) {
            this.gameService.addGame(gameAddDto);
            System.out.printf("Successfully added game %s%n", gameAddDto.getTitle());
        } else {
            this.validationUtil.getViolations(gameAddDto)
                    .stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
    }

    private void logOut() {
        this.userService.logOut();
    }

    private void loginUser(String[] input) {
        String email = input[1];
        String password = input[2];
        UserLogInDto userLogInDto = new UserLogInDto(email, password);
        User user = this.userService.loginUser(userLogInDto);
        if (user.getRole() == ADMIN) {
            this.adminUser = user;
        }
        this.userService.loginUser(userLogInDto);

    }

    private void registerUser(String[] input) {
        String email = input[1];
        String password = input[2];
        String confirmedPassword = input[3];
        String name = input[4];

        if (!password.equals(confirmedPassword)) {
            System.out.println("Incorrect username / password");
            return;
        }

        UserRegisterDto userRegisterDto = new UserRegisterDto(email, password, name);

        if (this.validationUtil.isValid(userRegisterDto)) {
            this.userService.registerUser(userRegisterDto);
            System.out.printf("Successfully added user %s%n", userRegisterDto.getFullName());
        } else {
            this.validationUtil.getViolations(userRegisterDto)
                    .stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

    }

    private void instructions() {
        System.out.println("Please, refresh you DB after every registration, adding or editing.");
        System.out.println("First of all you need to register the user by the following pattern:");
        System.out.println("RegisterUser|<email>|<password>|<confirmPassword>|<fullName>");
        System.out.println("After registration you can login with pattern:");
        System.out.println("LoginUser|ivan@ivan.com|Ivan12");
        System.out.println("And logout:");
        System.out.println("Command: Logout");
        System.out.println("Only as administrator you can add/edit or delete games from database.");
        System.out.println("Add pattern: AddGame|<title>|<price>|<size>|<trailer>|<thubnailURL>|<description>|<releaseDate>");
        System.out.println("Edit pattern: EditGame|<id>|<field>=<value>");
        System.out.println("Delete pattern: DeleteGame|<id>");
        System.out.println("Commands and patterns as administrator or user:");
        System.out.println("Command: AllGames -> this command returns all games from the database.");
        System.out.println("Command and pattern: DetailGame|<gameTitle> -> this command by game title " +
                "returns information about given game.");
        System.out.println("Command: OwnedGames -> this command returns all games that user/administrator have already buyed.");
        System.out.println("Buying commands and patterns as administrator or user:");
        System.out.println("Command: AddItem|<gameTitle> -> this command puts the given game in the cart.");
        System.out.println("Command: RemoveItem|<gameTitle> -> this command removes the given game from the cart.");
        System.out.println("Command: BuyItem -> this command buys one or more games from the cart");


    }
}
