package hiberspring.service.impl;

import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.EmployeeSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hiberspring.common.GlobalConstants.EMPLOYEES_FILE_PATH;

@Service
@Transactional

public class EmployeeServiceImpl implements EmployeeService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final BranchService branchService;
    private final EmployeeCardService employeeCardService;

    public EmployeeServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, EmployeeRepository employeeRepository, BranchService branchService, EmployeeCardService employeeCardService) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.branchService = branchService;
        this.employeeCardService = employeeCardService;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Paths.get(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        EmployeeSeedRootDto employeeSeedRootDto = this.xmlParser.unmarshalFromFile(EMPLOYEES_FILE_PATH, EmployeeSeedRootDto.class);

        System.out.println();
        employeeSeedRootDto.getEmployees().stream().forEach(employeeSeedDto -> {
            if (getEmployeeByFirstNameAndLastName(employeeSeedDto.getFirstName(), employeeSeedDto.getLastName()) != null) {
                sb.append("Already in DB").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(employeeSeedDto)) {
                if (employeeCardService.getFirstByNumber(employeeSeedDto.getCard()) != null) {
                    if (branchService.getBranchByName(employeeSeedDto.getBranch()) != null) {
                        Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);

                        EmployeeCard card = this.employeeCardService.getFirstByNumber(employeeSeedDto.getCard());

                        Branch branch = this.branchService.getBranchByName(employeeSeedDto.getBranch());

                        employee.setCard(card);
                        employee.setBranch(branch);
                        this.employeeRepository.saveAndFlush(employee);
                        sb.append(String.format("Successfully imported Employee %s %s.", employee.getFirstName(), employee.getLastName()));
                    } else {
                        sb.append("Branch doesn't exist").append(System.lineSeparator());
                    }
                } else {
                    sb.append("Card doesn't exist").append(System.lineSeparator());
                }
            } else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder stringBuilder = new StringBuilder();
        this.employeeRepository.getAllEmployeesWithBranchThatHaveAtLeastOneProduct().stream()
                .forEach(employee -> {
                    stringBuilder.append(String.format("Name: %s %s\n" +
                                    "Position: %s\n" +
                                    "Card Number: %s\n"
                            , employee.getFirstName(), employee.getLastName()
                            , employee.getPosition()
                            , employee.getCard().getNumber())).append(System.lineSeparator());
                });
        return stringBuilder.toString().trim();
    }

    @Override
    public Employee getEmployeeByFirstNameAndLastName(String fName, String lName) {
        return this.employeeRepository.findFirstByFirstNameAndLastName(fName, lName);
    }
}
