package main.java;

import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;
import lombok.SneakyThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Engine implements Runnable {
    private final EntityManager manager;
    private final BufferedReader reader;

    public Engine(EntityManager manager) {
        this.manager = manager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));

    }

    @SneakyThrows
    @Override
    public void run() {
        int currentTask = 3;

        //-------------------STEP 1-------------------
        //Before you run the project , make sure that your USERNAME and PASSWORD are correct ,
        // if they not enter your @localhost username and password

        //-------------------STEP 2-------------------
        // Hello, if you want to open the exercise, that you want,
        // just enter the number that exist of concretely Exercise.


        switch (currentTask) {
            case 2:
                System.out.println("2. Remove Objects");
                removeObjectEx();
                break;
            case 3:
                System.out.println("3. Contains Employee");
                try {
                    containsEmployeesEx();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                System.out.println("4. Employees with Salary Over 50 000");
                employeesWithSalaryOver50000();
                break;
            case 5:
                System.out.println("5. Employees from Department");
                employeesFromResearchDepartment();
                break;
            case 6:
                System.out.println("6. Adding a New Address and Updating Employee");
                try {
                    addingNewAddress();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                System.out.println("7. Addresses with Employee Count");
                countOfEmployeeByAddress();
                break;
            case 8:
                System.out.println("8. Get Employee with Project");
                employeeWithProject();
                break;
            case 9:
                System.out.println("9. Find Latest 10 Projects");
                latestTenProjects();
                break;
            case 10:
                System.out.println("10.\tIncrease Salaries");
                increaseSalary();
                break;
            case 11:
                System.out.println("11.\tRemove Towns");
                removeTownsWithAddresses();
                break;
            case 12:
                System.out.println("12.\tFind Employees by First Name");
                findEmployeesByName();
                break;
            case 13:
                System.out.println("13.\tEmployees Maximum Salaries\t");
                maximumSalary();
                break;

        }
    }

    private void maximumSalary() {
        manager.getTransaction().begin();
        manager.createQuery("select e, max(e.salary) from Employee e " +
                "join e.department d " +
                "group by d.name" +
                " having max (e.salary)not between 30000 and 70000", Object[].class)
                .getResultList()
                .forEach(result -> {
                    Employee employee = (Employee) result[0];
                    BigDecimal maxSalary = (BigDecimal) result[1];
                    System.out.printf("%s %s%n", employee.getDepartment().getName(), maxSalary);
                });

        manager.getTransaction().commit();
    }

    private void findEmployeesByName() throws IOException {
        System.out.println("Please enter the starts letters:");
        String input = reader.readLine();
        manager.getTransaction().begin();
        manager.createQuery("select e from Employee e where e.firstName like :param", Employee.class)
                .setParameter("param", input + "%")
                .getResultStream()
                .forEach(employee -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary()));
        manager.getTransaction().commit();
    }

    private void removeTownsWithAddresses() throws IOException {
        System.out.println("Enter the city name:");
        String townName = reader.readLine();

        int townId = (int) manager.createQuery("select t.id from Town t where t.name =: givenName")
                .setParameter("givenName", townName).getSingleResult();
        List<Integer> addressTownId = this.manager
                .createQuery("select id from Address where town.id = :tId", Integer.class).
                        setParameter("tId", townId).getResultList();

        manager.getTransaction().begin();
        manager.createQuery("update Employee set address = null where address.id in :nums")
                .setParameter("nums", addressTownId).executeUpdate();
        manager.createQuery("delete from Address where town.id = :townId")
                .setParameter("townId", townId).executeUpdate();
        manager.createQuery("delete from Town where id = :idTodelete")
                .setParameter("idTodelete", townId).executeUpdate();
        System.out.printf("%d address in %s deleted", addressTownId.size(), townName);
        manager.getTransaction().commit();

    }

    private void increaseSalary() {
        manager.getTransaction().begin();
        BigDecimal multiplayer = new BigDecimal(1.12);
        manager.createQuery("select e from Employee e " +
                "where e.department.name= 'Engineering'" +
                "or e.department.name= 'Tool Design'" +
                "or e.department.name= 'Marketing'" +
                "or e.department.name= 'Information Services'", Employee.class)
                .getResultStream().forEach(employee -> {
            employee.setSalary(employee.getSalary().multiply(multiplayer));
            System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
        });
        manager.getTransaction().commit();
    }

    private void latestTenProjects() {
        manager.getTransaction().begin();
        manager.createQuery("select p from Project p order by p.startDate desc ,p.name asc ", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("Project name: %s%n", project.getName());
                    System.out.printf("\t\tProject Description: %s%n", project.getDescription());
                    System.out.printf("\t\tProject Start Date: %s%n", project.getStartDate());
                    System.out.printf("\t\tProject End Date: %s%n", project.getEndDate());
                });
        manager.getTransaction().commit();
    }

    private void employeeWithProject() throws IOException {
        System.out.println("Enter employee id:");
        int givenId = Integer.parseInt(reader.readLine());
        manager.createQuery("select e from Employee e where e.id=: number", Employee.class)
                .setParameter("number", givenId)
                .getResultStream()
                .forEach(employee -> {
                            System.out.printf("%s %s - %s", employee.getFirstName()
                                    , employee.getLastName(),
                                    employee.getJobTitle());
                            employee.getProjects().stream()
                                    .sorted(Comparator.comparing(Project::getName))
                                    .forEach(project -> System.out.printf("%n\t\t%s", project.getName()));
                        }
                );
    }

    private void countOfEmployeeByAddress() {
        manager.getTransaction().begin();
        try {
            manager
                    .createQuery("select a from Address a order by a.employees.size desc", Address.class)
                    .setMaxResults(10)
                    .getResultList()
                    .forEach(address -> System.out.printf("%n%s, %s - %d employees",
                            address.getText(),
                            address.getTown().getName(),
                            address.getEmployees().size())
                    );
        } catch (NoResultException ex) {
            System.out.println("Please reload the database.");
        }
        manager.getTransaction().commit();
    }

    private void addingNewAddress() throws IOException {
        System.out.println("Enter employee last name:");
        String givenName = reader.readLine();

        Address address = createNewAddress("Vitoshka 15");
        manager.getTransaction().begin();
        Employee employee = manager.createQuery("select e from Employee e where e.lastName=: name", Employee.class)
                .setParameter("name", givenName).getSingleResult();
        manager.detach(employee);
        employee.setAddress(address);
        manager.merge(employee);
        manager.getTransaction().commit();
    }

    private Address createNewAddress(String textContent) {
        manager.getTransaction().begin();
        Address address1 = new Address();
        address1.setText(textContent);
        manager.persist(address1);
        manager.getTransaction().commit();
        return address1;
    }

    private void employeesFromResearchDepartment() {
        manager.getTransaction().begin();
        manager.createQuery("select e from Employee e where e.department.name='Research and Development'" +
                "order by e.salary asc, e.id asc  ", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
        manager.getTransaction().commit();

    }

    private void employeesWithSalaryOver50000() {
        manager.getTransaction().begin();
        manager.createQuery("select e from Employee e where e.salary>50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
        manager.getTransaction().commit();
    }

    private void containsEmployeesEx() throws IOException {
        System.out.println("Enter full name:");
        String customName = reader.readLine();
        manager.getTransaction().begin();
        try {

            manager.createQuery("select e from Employee as e where concat(e.firstName,' ',e.lastName) =:name ")
                    .setParameter("name", customName)
                    .getSingleResult();
            System.out.println("YES");
        } catch (NoResultException nre) {
            System.out.println("NO");
        }


        manager.getTransaction().commit();
    }

    private void removeObjectEx() {
        List<Town> towns = manager.createQuery("SELECT  t from Town t WHERE length(t.name)>5").getResultList();
        manager.getTransaction().begin();
        towns.stream().forEach(this.manager::detach);

        manager.createQuery("SELECT t FROM Town t", Town.class)
                .getResultList().forEach(t -> t.setName(t.getName().toLowerCase()));
        towns.stream().forEach(manager::merge);
        manager.getTransaction().commit();

    }
}
