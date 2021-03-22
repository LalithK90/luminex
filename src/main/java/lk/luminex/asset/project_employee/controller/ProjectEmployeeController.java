package lk.luminex.asset.project_employee.controller;

import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.common_asset.model.enums.Title;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import lk.luminex.asset.project_employee.service.ProjectEmployeeService;
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
@RequestMapping("/projectEmployee")
public  class ProjectEmployeeController implements AbstractController< ProjectEmployee, Integer> {
    private final ProjectEmployeeService projectEmployeeService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final EmailService emailService;
    private final TwilioMessageService twilioMessageService;

    @Autowired
    public ProjectEmployeeController(ProjectEmployeeService projectEmployeeService, MakeAutoGenerateNumberService makeAutoGenerateNumberService, EmailService emailService, TwilioMessageService twilioMessageService) {
        this.projectEmployeeService = projectEmployeeService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.emailService = emailService;
        this.twilioMessageService = twilioMessageService;
    }

    private String commonThings(Model model, ProjectEmployee projectEmployee, Boolean addState) {
        model.addAttribute("title", Title.values());
        model.addAttribute("projectEmployee", projectEmployee);
        model.addAttribute("addStatus", addState);
        return "projectEmployee/addprojectEmployee";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("projectEmployees", projectEmployeeService.findAll().stream()
            .filter(x-> LiveDead.ACTIVE.equals(x.getLiveDead()))
            .collect(Collectors.toList()));
        return "projectEmployee/projectEmployee";
    }

    @Override
    public String findById(Integer id, Model model) {
        return null;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        return commonThings(model, new ProjectEmployee(), true);
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(ProjectEmployee projectEmployee, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThings(model, projectEmployee, true);
        }

        redirectAttributes.addFlashAttribute("projectEmployeeDetail", projectEmployeeService.persist(projectEmployee));
        return "redirect:/projectEmployee";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThings(model, projectEmployeeService.findById(id), false);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        projectEmployeeService.delete(id);
        return "redirect:/projectEmployee";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("projectEmployeeDetail", projectEmployeeService.findById(id));
        return "projectEmployee/projectEmployee-detail";
    }
}
