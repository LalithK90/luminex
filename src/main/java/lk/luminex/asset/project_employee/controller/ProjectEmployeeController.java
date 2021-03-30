package lk.luminex.asset.project_employee.controller;

import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.employee.service.EmployeeService;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.project.service.ProjectService;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import lk.luminex.asset.project_employee.entity.enums.ProjectEmployeeStatus;
import lk.luminex.asset.project_employee.service.ProjectEmployeeService;
import org.hibernate.criterion.ProjectionList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    List< Employee > employees = employeeService.findAll();
    List< Employee > employeeAlready = new ArrayList<>();

    project.getProjectEmployees().forEach(x -> employeeAlready.add(employeeService.findById(x.getEmployee().getId())));
   //remove duplicates employee
    employees.removeAll(new HashSet<>(employeeAlready));

    model.addAttribute("projectDetail", project);
    model.addAttribute("employees", employees);
    model.addAttribute("project", project);
    return "projectEmployee/addProjectEmployee";
  }

  @PostMapping( "/save" )
  public String persist(@ModelAttribute Project project) {
    project.getProjectEmployees().forEach(projectEmployeeService::persist);
    return "redirect:/project";
  }


  @GetMapping( "/edit/{id}" )
  public String addEdit(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("project", projectService.findById(id));
    model.addAttribute("projectEmployeeStatuses", ProjectEmployeeStatus.values());
    return "projectEmployee/editProjectEmployee";
  }


}
