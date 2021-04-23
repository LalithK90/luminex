package lk.luminex.asset.employee.controller;

import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.employee.entity.enums.EmployeeStatus;
import lk.luminex.asset.employee.service.EmployeeService;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/testreport")
public class EmployeeReport {

    private final EmployeeService employeeService;

    public EmployeeReport(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/se")
    public String employeeSelect(@NonNull Model model) {
        model.addAttribute("showStatus", false);
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("employee", new Employee());
        return "testreport/empsel";
    }

    @PostMapping("/employee/seget")
    public String employeeGet(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
        EmployeeStatus status = employee.getEmployeeStatus();
        List<Employee> employees = new ArrayList<>();
        for (Employee employe : employeeService.findAll().stream().filter(x -> status.equals(x.getEmployeeStatus())).collect(Collectors.toList())) {
            employees.add(employe);
        }
        model.addAttribute("balla", employees);
        model.addAttribute("showStatus", true);
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        return "testreport/empsel";
    }
}