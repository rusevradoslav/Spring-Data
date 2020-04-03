package hiberspring.service;

import hiberspring.domain.entities.Employee;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

//TODO
public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesXmlFile() throws IOException;

    String importEmployees() throws JAXBException, FileNotFoundException;

    String exportProductiveEmployees();

    Employee getEmployeeByFirstNameAndLastName(String fName, String lName);
}
