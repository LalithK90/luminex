package lk.luminex.asset.order.controller;


import lk.luminex.asset.order_ledger.entity.OrderLedger;
import lk.luminex.asset.order.entity.Order;
import lk.luminex.asset.order.entity.enums.OrderState;
import lk.luminex.asset.project.service.ProjectService;
import lk.luminex.asset.order.service.OrderService;
import lk.luminex.asset.ledger.controller.LedgerController;
import lk.luminex.asset.ledger.entity.Ledger;
import lk.luminex.asset.ledger.service.LedgerService;
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
@RequestMapping( "/order" )
public class OrderController {
  private final OrderService orderService;
  private final ProjectService projectService;
  private final LedgerService ledgerService;
  private final DateTimeAgeService dateTimeAgeService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final TwilioMessageService twilioMessageService;

  public OrderController(OrderService orderService, ProjectService projectService,
                         LedgerService ledgerService, DateTimeAgeService dateTimeAgeService,

                         MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                         TwilioMessageService twilioMessageService) {
    this.orderService = orderService;
    this.projectService = projectService;
    this.ledgerService = ledgerService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.twilioMessageService = twilioMessageService;
  }

  @GetMapping
  public String order(Model model) {
    model.addAttribute("orders",
                       orderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(dateTimeAgeService.getPastDateByMonth(3)), dateTimeAgeService.dateTimeToLocalDateEndInDay(LocalDate.now())));
    model.addAttribute("firstOrderMessage", true);
    return "order/order";
  }

  @GetMapping( "/search" )
  public String orderSearch(@RequestAttribute( "startDate" ) LocalDate startDate,
                              @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
    model.addAttribute("orders",
                       orderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate), dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate)));
    model.addAttribute("firstOrderMessage", true);
    return "order/order";
  }

  private String common(Model model, Order order) {
    model.addAttribute("order", order);
    model.addAttribute("projects", projectService.findAll());
    model.addAttribute("ledgerItemURL", MvcUriComponentsBuilder
        .fromMethodName(LedgerController.class, "findId", "")
        .build()
        .toString());
    //send not expired and not zero quantity
    model.addAttribute("ledgers", ledgerService.findAll()
        .stream()
        .filter(x -> 0 < Integer.parseInt(x.getQuantity()) && x.getExpiredDate().isAfter(LocalDate.now()))
        .collect(Collectors.toList()));
    return "order/addOrder";
  }

  @GetMapping( "/add/{id}" )
  public String getOrderForm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("projectDetail", projectService.findById(id));
    return common(model, new Order());
  }

  @GetMapping( "/{id}" )
  public String viewDetails(@PathVariable Integer id, Model model) {
    Order order = orderService.findById(id);
    model.addAttribute("orderDetail", order);
    model.addAttribute("customerDetail", order.getProject());
    return "order/order-detail";
  }

  @PostMapping
  public String persistOrder(@Valid @ModelAttribute Order order, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      return common(model, order);
    }
    if ( order.getId() == null ) {
      if ( orderService.findByLastOrder() == null ) {
        //need to generate new one
        order.setCode("LCPO" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {

        //if there is customer in db need to get that customer's code and increase its value
        String previousCode = orderService.findByLastOrder().getCode().substring(4);
        order.setCode("LCPO" + makeAutoGenerateNumberService.numberAutoGen(previousCode).toString());
      }
    }

    List< OrderLedger > orderLedgers = new ArrayList<>();

    order.getOrderLedgers().forEach(x -> {
      x.setOrder(order);
      orderLedgers.add(x);
    });
    order.setOrderLedgers(orderLedgers);
    order.setOrderState(OrderState.PENDING);
    Order saveOrder = orderService.persist(order);

    for ( OrderLedger orderLedger : saveOrder.getOrderLedgers() ) {
      Ledger ledger = ledgerService.findById(orderLedger.getLedger().getId());
      String quantity = orderLedger.getQuantity();
      int availableQuantity = Integer.parseInt(ledger.getQuantity());
      int sellQuantity = Integer.parseInt(quantity);
      ledger.setQuantity(String.valueOf(availableQuantity - sellQuantity));
      ledgerService.persist(ledger);
    }
    return "redirect:/project";
  }


  @GetMapping( "/remove/{id}" )
  public String removeOrder(@PathVariable( "id" ) Integer id) {
    Order order = orderService.findById(id);
    order.setOrderState(OrderState.CANCELED);
    orderService.persist(order);
    return "redirect:/order";
  }


}
