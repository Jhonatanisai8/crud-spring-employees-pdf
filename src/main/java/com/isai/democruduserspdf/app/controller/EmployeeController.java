package com.isai.democruduserspdf.app.controller;

import com.isai.democruduserspdf.app.models.Employee;
import com.isai.democruduserspdf.app.repositorys.EmployeeRepository;
import com.isai.democruduserspdf.app.utils.pagination.PageRender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Objects;

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

    @GetMapping(path = "/view-employee-detail/{employeeId}")
    public String viewEmployeeDetail(
            @PathVariable(value = "employeeId") Long employeeId,
            Map<String, Object> model,
            RedirectAttributes flash) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        if (Objects.isNull(employee)) {
            flash.addFlashAttribute("message", "El empleado no existe.");
            return "redirect:/list";
        }
        model.put("employee", employee);
        model.put("tittle", "Detalle del empleado.".concat(employee.getFirstName()));
        return "view-employee-details";
    }

    @GetMapping(path = "/register-employee")
    public String mostrarFormularioRegistrarEmpleado(
            Map<String, Object> model) {
        Employee employee = new Employee();
        model.put("tittle", "Registrar Empleado");
        model.put("employee", employee);
        return "form-employee";
    }

    @PostMapping(path = "/register-employee")
    public String saveEmployee(
            @Valid Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("tittle", "Registrar Empleado");
            return "form-employee";
        }
        String message = (employee.getIdEmployee() != null) ? "Empleado actualizado exitosamente." : "Empleado registrado exitosamente.";
        employeeRepository.save(employee);
        status.setComplete();
        flash.addFlashAttribute("message", message);
        return "redirect:/list";
    }

    @GetMapping(path = "/edit-employee/{employeeId}")
    public String editEmployee(
            @PathVariable(value = "employeeId") Long employeeId,
            Map<String, Object> model,
            RedirectAttributes flash) {
        Employee employee = null;
        if (employeeId > 0) {
            flash.addFlashAttribute("error", "El empleado no existe.");
            employee = employeeRepository.findById(employeeId).orElseThrow();
            if (Objects.isNull(employee)) {
                flash.addFlashAttribute("error", "El empleado no existe.");
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("message", "El empleado no puede ser cero.");
            return "redirect:/list";
        }
        model.put("tittle", "Editar Empleado");
        model.put("employee", employee);
        return "form-employee";
    }
}
