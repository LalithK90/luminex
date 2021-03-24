package lk.luminex.asset.project_employee.controller;

import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.employee.service.EmployeeService;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.project.service.ProjectService;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import lk.luminex.asset.project_employee.service.ProjectEmployeeService;
import org.hibernate.criterion.ProjectionList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/projectEmployee" )
public class ProjectEmployeeController {
  private final ProjectEmployeeService projectEmployeeService;
  private final EmployeeService employeeService;
  private final ProjectService projectService;

  public ProjectEmployeeController(ProjectEmployeeService projectEmployeeService, EmployeeService employeeService,
                                   ProjectService projectService) {
    this.projectEmployeeService = projectEmployeeService;
    this.employeeService = employeeService;
    this.projectService = projectService;
  }


  @GetMapping( "/add/{id}" )
  public String addForm(@PathVariable( "id" ) Integer id, Model model) {
    Project project = projectService.findById(id);

    model.addAttribute("projectDetail", project);
    List< Employee > employees = new ArrayList<>();

    for ( Employee employee : employeeService.findAll() ) {
      for ( ProjectEmployee projectEmployee : project.getProjectEmployees() ) {
        if ( !projectEmployee.getEmployee().equals(employee) ) {
          employees.add(employee);
        }
      }
    }
model.addAttribute("projectDetail", project);
    model.addAttribute("employees", employees.stream().distinct().collect(Collectors.toList()));
    return "projectEmployee/addProjectEmployee";
  }

}
