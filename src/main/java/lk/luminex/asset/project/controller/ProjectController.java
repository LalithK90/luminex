package lk.luminex.asset.project.controller;


import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.common_asset.model.enums.Title;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.project.entity.enums.ProjectStatus;
import lk.luminex.asset.project.service.ProjectService;
import lk.luminex.util.interfaces.AbstractController;
import lk.luminex.util.service.EmailService;
import lk.luminex.util.service.MakeAutoGenerateNumberService;
import lk.luminex.util.service.TwilioMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
@RequestMapping( "/project" )
public class ProjectController implements AbstractController< Project, Integer > {
  private final ProjectService projectService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final EmailService emailService;
  private final TwilioMessageService twilioMessageService;

  @Autowired
  public ProjectController(ProjectService projectService, MakeAutoGenerateNumberService makeAutoGenerateNumberService
      , EmailService emailService, TwilioMessageService twilioMessageService) {
    this.projectService = projectService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.emailService = emailService;
    this.twilioMessageService = twilioMessageService;
  }

  private String commonThings(Model model, Project project, Boolean addState) {
    model.addAttribute("project", project);
    model.addAttribute("addStatus", addState);
    model.addAttribute("projectStatuses", ProjectStatus.values());
    return "project/addProject";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("projects", projectService.findAll().stream()
        .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
        .collect(Collectors.toList()));
    return "project/project";
  }

  @Override
  public String findById(Integer id, Model model) {
    return null;
  }

  @GetMapping( "/add" )
  public String addForm(Model model) {
    return commonThings(model, new Project(), true);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(Project project, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonThings(model, project, true);
    }

    if ( project.getId() == null ) {
      Project lastProject = projectService.lastProject();
      if ( lastProject == null ) {
        project.setCode("LMP" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        project.setCode("LMP" + makeAutoGenerateNumberService.numberAutoGen(lastProject.getCode().substring(3)).toString());
      }
    }

    redirectAttributes.addFlashAttribute("projectDetail", projectService.persist(project));
    return "redirect:/project";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonThings(model, projectService.findById(id), false);
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    projectService.delete(id);
    return "redirect:/project";
  }

  @GetMapping( "/{id}" )
  public String view(@PathVariable Integer id, Model model) {
    model.addAttribute("projectDetail", projectService.findById(id));
    return "project/project-detail";
  }
}
