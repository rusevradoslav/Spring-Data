package alararestaurant.service.impl;

import alararestaurant.domain.dtos.seedOrders.OrderSeedRootDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.service.EmployeeService;
import alararestaurant.service.OrderService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static alararestaurant.constants.GlobalConstants.ORDER_FILE_PATH;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final EmployeeService employeeService;
    private final PositionRepository positionRepository;

    public OrderServiceImpl(ValidationUtil validationUtil, FileUtil fileUtil, ModelMapper modelMapper, XmlParser xmlParser, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository, EmployeeService employeeService, PositionRepository positionRepository) {
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
    }


    @Override
    public Boolean ordersAreImported() {

        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {

        return fileUtil.readFileContinent(ORDER_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        OrderSeedRootDto orderItemSeedRootDtos = this.xmlParser.unmarshalFromFile(ORDER_FILE_PATH, OrderSeedRootDto.class);
        orderItemSeedRootDtos.getOrders().stream().forEach(orderSeedDto -> {
            if (validationUtil.isValid(orderSeedDto)) {
                if (employeeService.getEmployeeByName(orderSeedDto.getEmployee()) != null) {
                    Order order = new Order();
                    order.setCustomer(orderSeedDto.getCustomer());
                    order.setDateTime(orderSeedDto.getDateTime());
                    order.setType(OrderType.valueOf(orderSeedDto.getType()));
                    Employee employee = this.employeeService.getEmployeeByName(orderSeedDto.getEmployee());

                    order.setEmployee(employee);

                    List<OrderItem> orderItems = new ArrayList<>();


                    orderSeedDto.getItems().getItems().stream().forEach(orderItemSeedDto -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setItem(itemRepository.findFirstByName(orderItemSeedDto.getName()));
                        orderItem.setQuantity(orderItemSeedDto.getQuantity());
                        orderItem.setOrder(order);
                        orderItemRepository.save(orderItem);
                        orderItems.add(orderItem);
                    });
                    order.setOrderItems(orderItems);
                    orderRepository.saveAndFlush(order);

                    sb.append(String.format("Order for %s on %s added", order.getCustomer(), order.getDateTime()));
                } else {
                    sb.append("Invalid data").append(System.lineSeparator());
                }
            } else {
                sb.append("Invalid data");

            }
            sb.append(System.lineSeparator());


        });

        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Position neededPosition = this.positionRepository.findPositionByName("Burger Flipper");
        List<Order> ordersList = this.orderRepository.findAllByEmployeePosition(neededPosition)
                .stream()
                .sorted((f, s) -> {
                    int result = f.getEmployee().getName().compareTo(s.getEmployee().getName());
                    if (result == 0) {
                        result = (int) (f.getId() - s.getId());
                    }
                    return result;
                })
                .collect(Collectors.toList());
        for (Order order : ordersList) {
            sb.append(String.format("Name: %s%n" +
                            "Orders:%n" +
                            "   Customer: %s%n" +
                            "   Items:%n",
                    order.getEmployee().getName(),
                    order.getCustomer()));
            for (OrderItem orderItem : order.getOrderItems()) {
                sb.append(String.format("       Name: %s%n" +
                                "       Price: %.2f%n" +
                                "       Quantity: %d%n",
                        orderItem.getItem().getName(),
                        orderItem.getItem().getPrice(),
                        orderItem.getQuantity()))
                        .append(System.lineSeparator());
            }

        }
        return sb.toString().trim();
    }
}

