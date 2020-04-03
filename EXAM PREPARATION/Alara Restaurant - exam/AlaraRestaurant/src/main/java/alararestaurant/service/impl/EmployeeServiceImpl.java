package alararestaurant.service.impl;

import alararestaurant.constants.GlobalConstants;
import alararestaurant.domain.dtos.EmployeeSeedDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.service.EmployeeService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static alararestaurant.constants.GlobalConstants.EMPLOYEE_FILE_PATH;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    public EmployeeServiceImpl(FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;

    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFileContinent(EMPLOYEE_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        EmployeeSeedDto[] employeeSeedDtos = this.gson.fromJson(new FileReader(EMPLOYEE_FILE_PATH), EmployeeSeedDto[].class);

        Arrays.stream(employeeSeedDtos).forEach(employeeSeedDto -> {
            if (validationUtil.isValid(employeeSeedDto)) {
                Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);

                if (positionRepository.findFirstByName(employeeSeedDto.getPosition()) == null){
                    Position position = new Position();
                    position.setName(employeeSeedDto.getPosition());
                    this.positionRepository.saveAndFlush(position);
                }
                employee.setPosition(positionRepository.findFirstByName(employeeSeedDto.getPosition()));

                employeeRepository.saveAndFlush(employee);

                sb.append(String.format("Record %s successfully imported.",employee.getName()));

            } else {
                sb.append("Invalid data");
            }
            sb.append(System.lineSeparator());
        });


        return sb.toString();
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return this.employeeRepository.findFirstByName(name);
    }


}
