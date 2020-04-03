package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.tickets.TicketSeedRootDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GloblalConstants.TICKET_FILE_PATH;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;

    public TicketServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, TicketRepository ticketRepository, PlaneRepository planeRepository, TownRepository townRepository, PassengerRepository passengerRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.ticketRepository = ticketRepository;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKET_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TicketSeedRootDto ticketSeedRootDto = this.xmlParser.unmarshalFromFile(TICKET_FILE_PATH, TicketSeedRootDto.class);
        System.out.println();
        ticketSeedRootDto.getTickets().stream().forEach(ticketSeedDto -> {
            if (getTicketBySerialNumber(ticketSeedDto.getSerialNumber())) {
                sb.append("Already exist in DB").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(ticketSeedDto)) {
                if (this.townRepository.findFirstByName(ticketSeedDto.getFromTown().getName()) != null) {
                    if (this.townRepository.findFirstByName(ticketSeedDto.getToTown().getName()) != null) {
                        if (this.passengerRepository.findFirstByEmail(ticketSeedDto.getPassenger().getEmail()) != null) {
                            if (this.planeRepository.findFirstByRegisterNumber(ticketSeedDto.getPlane().getRegisterNumber()) != null) {
                                Ticket ticket = new Ticket();
                                ticket.setSerialNumber(ticketSeedDto.getSerialNumber());
                                ticket.setPrice(ticketSeedDto.getPrice());
                                ticket.setTakeOff(ticketSeedDto.getTakeOff());

                                Town fromTown = this.townRepository.findFirstByName(ticketSeedDto.getFromTown().getName());
                                Town toTown = this.townRepository.findFirstByName(ticketSeedDto.getToTown().getName());
                                Passenger passenger = this.passengerRepository.findFirstByEmail(ticketSeedDto.getPassenger().getEmail());
                                Plane plane = this.planeRepository.findFirstByRegisterNumber(ticketSeedDto.getPlane().getRegisterNumber());

                                ticket.setFromTown(fromTown);
                                ticket.setToTown(toTown);
                                ticket.setPassenger(passenger);
                                ticket.setPlane(plane);
                                System.out.println();
                                this.ticketRepository.saveAndFlush(ticket);
                                sb.append(String.format("Successfully imported Ticket %s - %s",ticket.getFromTown().getName(),ticket.getToTown().getName()));
                                passenger.getTickets().add(ticket);

                            } else {
                                sb.append("Invalid Ticket").append(System.lineSeparator());
                                return;
                            }
                        } else {
                            sb.append("Invalid Ticket").append(System.lineSeparator());
                            return;
                        }
                    } else {
                        sb.append("Invalid Ticket").append(System.lineSeparator());
                        return;
                    }
                } else {
                    sb.append("Invalid Ticket").append(System.lineSeparator());
                    return;
                }
            } else {
                sb.append("Invalid Ticket!");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString().trim();
    }

    private boolean getTicketBySerialNumber(String serialNumber) {
        return this.ticketRepository.findFirstBySerialNumber(serialNumber) != null;
    }
}
