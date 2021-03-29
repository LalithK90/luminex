package lk.luminex.asset.project_order.controller;

import lk.luminex.asset.ledger.controller.LedgerController;
import lk.luminex.asset.ledger.entity.Ledger;
import lk.luminex.asset.ledger.service.LedgerService;
import lk.luminex.asset.order_ledger.entity.OrderLedger;
import lk.luminex.asset.project.service.ProjectService;
import lk.luminex.asset.project_order.entity.ProjectOrder;
import lk.luminex.asset.project_order.entity.enums.OrderState;
import lk.luminex.asset.project_order.service.ProjectOrderService;
import lk.luminex.util.service.DateTimeAgeService;
import lk.luminex.util.service.MakeAutoGenerateNumberService;
import lk.luminex.util.service.TwilioMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/projectOrder" )
public class ProjectOrderController {
  private final ProjectOrderService projectOrderService;
  private final ProjectService projectService;
  private final LedgerService ledgerService;
  private final DateTimeAgeService dateTimeAgeService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final TwilioMessageService twilioMessageService;

  public ProjectOrderController(ProjectOrderService projectOrderService, ProjectService projectService,
                                LedgerService ledgerService, DateTimeAgeService dateTimeAgeService,

                                MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                                TwilioMessageService twilioMessageService) {
    this.projectOrderService = projectOrderService;
    this.projectService = projectService;
    this.ledgerService = ledgerService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.twilioMessageService = twilioMessageService;
  }

  @GetMapping
  public String projectOrder(Model model) {
    model.addAttribute("projectOrders",
                       projectOrderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(dateTimeAgeService.getPastDateByMonth(3)), dateTimeAgeService.dateTimeToLocalDateEndInDay(LocalDate.now())));
    model.addAttribute("firstOrderMessage", true);
    return "projectOrder/projectOrder";
  }

  @GetMapping( "/search" )
  public String projectOrderSearch(@RequestAttribute( "startDate" ) LocalDate startDate,
                              @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    model.addAttribute("projectOrders",
                       projectOrderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate), dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate)));
    model.addAttribute("firstOrderMessage", true);
    return "projectOrder/projectOrder";
  }

  private String common(Model model, ProjectOrder projectOrder) {
    model.addAttribute("projectOrder", projectOrder);
    model.addAttribute("projects", projectService.findAll());
    model.addAttribute("ledgerItemURL", MvcUriComponentsBuilder
        .fromMethodName(LedgerController.class, "findId", "")
        .build()
        .toString());
    //send not expired and not zero quantity
    model.addAttribute("ledgers", ledgerService.findAll());
    return "projectOrder/addOrder";
  }

  @GetMapping( "/add/{id}" )
  public String getOrderForm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("projectDetail", projectService.findById(id));
    return common(model, new ProjectOrder());
  }

  @GetMapping( "/{id}" )
  public String viewDetails(@PathVariable Integer id, Model model) {
    ProjectOrder projectOrder = projectOrderService.findById(id);
    model.addAttribute("projectOrderDetail", projectOrder);
    model.addAttribute("customerDetail", projectOrder.getProject());
    return "projectOrder/projectOrder-detail";
  }

  @PostMapping
  public String persistOrder(@Valid @ModelAttribute ProjectOrder projectOrder, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      return common(model, projectOrder);
    }
    if ( projectOrder.getId() == null ) {
      if ( projectOrderService.findByLastOrder() == null ) {
        //need to generate new one
        projectOrder.setCode("LCPO" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {

        //if there is customer in db need to get that customer's code and increase its value
        String previousCode = projectOrderService.findByLastOrder().getCode().substring(4);
        projectOrder.setCode("LCPO" + makeAutoGenerateNumberService.numberAutoGen(previousCode).toString());
      }
    }

    List< OrderLedger > projectOrderLedgers = new ArrayList<>();

    projectOrder.getOrderLedgers().forEach(x -> {
      x.setProjectOrder(projectOrder);
      projectOrderLedgers.add(x);
    });
    projectOrder.setOrderLedgers(projectOrderLedgers);
    projectOrder.setOrderState(OrderState.PENDING);
    ProjectOrder saveProjectOrder = projectOrderService.persist(projectOrder);

    for ( OrderLedger projectOrderLedger : saveProjectOrder.getOrderLedgers() ) {
      Ledger ledger = ledgerService.findById(projectOrderLedger.getLedger().getId());
      String quantity = projectOrderLedger.getQuantity();
      int availableQuantity = Integer.parseInt(ledger.getQuantity());
      int sellQuantity = Integer.parseInt(quantity);
      ledger.setQuantity(String.valueOf(availableQuantity - sellQuantity));
      ledgerService.persist(ledger);
    }
    return "redirect:/project";
  }


  @GetMapping( "/remove/{id}" )
  public String removeOrder(@PathVariable( "id" ) Integer id) {
    ProjectOrder projectOrder = projectOrderService.findById(id);
    projectOrder.setOrderState(OrderState.CANCELED);
    projectOrderService.persist(projectOrder);
    return "redirect:/projectOrder";
  }


}
