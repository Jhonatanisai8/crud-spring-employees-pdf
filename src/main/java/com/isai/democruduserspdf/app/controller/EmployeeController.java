package com.isai.democruduserspdf.app.controller;

import com.isai.democruduserspdf.app.models.Employee;
import com.isai.democruduserspdf.app.repositorys.EmployeeRepository;
import com.isai.democruduserspdf.app.utils.pagination.PageRender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @RequestMapping(path = {"/", "/list"}, method = RequestMethod.GET)
    public String listEmployees(@RequestParam(name = "page", defaultValue = "0") int page,
                                Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Employee> employees = employeeRepository.findAll(pageRequest);
        PageRender<Employee> pageRender = new PageRender<>("/list", employees);
        model.addAttribute("tittle", "Listado de Empleados.");
        model.addAttribute("employees", employees);
        model.addAttribute("pageRender", pageRender);
        return "list-employees";
    }
}
