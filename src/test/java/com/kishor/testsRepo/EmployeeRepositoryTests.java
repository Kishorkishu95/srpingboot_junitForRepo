package com.kishor.testsRepo;

import com.kishor.dao.EmployeeRepository;
import com.kishor.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit for Save an employee

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployee(){
        Employee employee1=Employee.builder().name("Anu").email("anu@gmail.com").build();
        employeeRepository.save(employee1);
        Assertions.assertThat(employee1.getId()).isGreaterThan(0);

    }

    // JUnit test for get a single Employee
    @Test
    @Order(2)
    public void getEmployee(){
        Employee employee1=employeeRepository.findById(4L).get();
        Assertions.assertThat(employee1.getId()).isEqualTo(4L);
    }

    // JUnit test for get a list of Employees
    @Test
    @Order(3)
    public void getAllEmployees(){
        List<Employee> employee1=employeeRepository.findAll();
        Assertions.assertThat(employee1.size()).isGreaterThan(0);
    }

    // JUnit test for updating an Employee
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployee(){
        Employee employee1=employeeRepository.findById(4L).get();
        employee1.setName("Anu");
        employee1.setEmail("kishAnu@gmail.com");
        Employee updatedEmployee=employeeRepository.save(employee1);
        Assertions.assertThat(updatedEmployee.getName()).isEqualTo("Anu");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployee(){
        Employee employee1=employeeRepository.findById(4L).get();
        employeeRepository.delete(employee1);
        Employee employee=null;
        Optional<Employee> optionalEmployee=employeeRepository.findByName("Anu");
       if(optionalEmployee.isPresent()){
           employee=optionalEmployee.get();
       }
        Assertions.assertThat(employee).isNull();
    }
}
