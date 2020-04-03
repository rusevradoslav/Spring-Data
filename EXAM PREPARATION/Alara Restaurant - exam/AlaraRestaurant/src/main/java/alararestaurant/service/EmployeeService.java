package alararestaurant.service;

import alararestaurant.domain.entities.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile() throws IOException;

    String importEmployees(String employees) throws FileNotFoundException;

   Employee getEmployeeByName(String name);
}
