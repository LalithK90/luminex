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
import java.util.stream.Collectors;

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

  private String commonMethod(Model model, String formAction, LocalDate startDate, LocalDate endDate,
                              List< ProjectOrder > projectOrders) {
    model.addAttribute("formAction", formAction);
    model.addAttribute("projectOrders", projectOrders);
    model.addAttribute("firstOrderMessage", true);
    model.addAttribute("message", "Following table show details belongs since" + startDate + " to " + endDate +
        "there month. if you need to more please search using above method");
    return "projectOrder/projectOrder";
  }


  @GetMapping
  public String projectOrder(Model model) {
    LocalDate startDate = dateTimeAgeService.getPastDateByMonth(3);
    LocalDate endDate = LocalDate.now();
    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                     dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate));
    return commonMethod(model, "/projectOrder/search", startDate, endDate, projectOrders);
  }

  @GetMapping( "/search" )
  public String projectOrderSearch(@RequestAttribute( "startDate" ) LocalDate startDate,
                                   @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                     dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate));
    return commonMethod(model, "/projectOrder/search", startDate, endDate, projectOrders);
  }

  @GetMapping( "/pendingOrder" )
  public String projectOrderPending(Model model) {
    LocalDate startDate = dateTimeAgeService.getPastDateByMonth(3);
    LocalDate endDate = LocalDate.now();

    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.PENDING);
    return commonMethod(model, "/projectOrder/pendingOrder/search", startDate, endDate, projectOrders);
  }

  @GetMapping( "/pendingOrder/search" )
  public String projectOrderSearchPendingOrder(@RequestAttribute( "startDate" ) LocalDate startDate,
                                               @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.PENDING);
    return commonMethod(model, "/projectOrder/pendingOrder/search", startDate, endDate, projectOrders);

  }

  @GetMapping( "/approved" )
  public String projectOrderApproved(Model model) {
    LocalDate startDate = dateTimeAgeService.getPastDateByMonth(3);
    LocalDate endDate = LocalDate.now();

    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.APPROVED);
    return commonMethod(model, "/projectOrder/approved/search", startDate, endDate, projectOrders);
  }

  @GetMapping( "/approved/search" )
  public String projectOrderSearchApproved(@RequestAttribute( "startDate" ) LocalDate startDate,
                                           @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.APPROVED);
    return commonMethod(model, "/projectOrder/approved/search", startDate, endDate, projectOrders);

  }

  @GetMapping( "/cancel" )
  public String projectOrderCancel(Model model) {
    LocalDate startDate = dateTimeAgeService.getPastDateByMonth(3);
    LocalDate endDate = LocalDate.now();

    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.CANCELED);
    return commonMethod(model, "/projectOrder/approved/search", startDate, endDate, projectOrders);
  }

  @GetMapping( "/cancel/search" )
  public String projectOrderSearchCancel(@RequestAttribute( "startDate" ) LocalDate startDate,
                                         @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    List< ProjectOrder > projectOrders =
        projectOrderService.findByCreatedAtIsBetweenAndOrderState(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate),
                                                                  dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate), OrderState.CANCELED);
    return commonMethod(model, "/projectOrder/approved/search", startDate, endDate, projectOrders);

  }

  private String common(Model model, ProjectOrder projectOrder) {
    model.addAttribute("projectOrder", projectOrder);
    model.addAttribute("projects", projectService.findAll());
    model.addAttribute("ledgerItemURL", MvcUriComponentsBuilder
        .fromMethodName(LedgerController.class, "findId", "")
        .build()
        .toString());
    //send not expired and not zero quantity
    model.addAttribute("ledgers",
                       ledgerService.findAll().stream().filter(x -> 0 < Integer.parseInt(x.getQuantity())).collect(Collectors.toList()));
    return "projectOrder/addProjectOrder";
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
    model.addAttribute("projectDetail", projectOrder.getProject());
    return "projectOrder/projectOrder-detail";
  }

  @PostMapping
  public String persistOrder(@Valid @ModelAttribute ProjectOrder projectOrder, BindingResult bindingResult,
                             Model model) {
    if ( bindingResult.hasErrors() ) {
   bindingResult.getAllErrors().forEach(System.out::println);
      projectOrder.setProject(projectService.findById(projectOrder.getProject().getId()));
    //  return common(model, projectOrder);

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
    return "redirect:/projectOrder";
  }

  @GetMapping( "/remove/{id}" )
  public String removeOrder(@PathVariable( "id" ) Integer id) {
    ProjectOrder projectOrder = projectOrderService.findById(id);
    projectOrder.setOrderState(OrderState.CANCELED);
    projectOrderService.persist(projectOrder);


    return "redirect:/projectOrder";
  }

  @GetMapping( "/approve/{id}" )
  public String approveOrder(@PathVariable( "id" ) Integer id) {
    ProjectOrder projectOrder = projectOrderService.findById(id);
    projectOrder.setOrderState(OrderState.APPROVED);
    projectOrderService.persist(projectOrder);
    return "redirect:/projectOrder";
  }

}
