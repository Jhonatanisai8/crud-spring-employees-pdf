package com.isai.democruduserspdf.app.service;

import com.isai.democruduserspdf.app.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAll();

    Page<Employee> findAll(Pageable pageable);

    void save(Employee employee);

    Employee findById(Long id);

    void delete(Employee employee);
}
