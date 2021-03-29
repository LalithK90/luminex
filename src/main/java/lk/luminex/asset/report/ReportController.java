package lk.luminex.asset.report;

import lk.luminex.asset.common_asset.model.NameCount;
import lk.luminex.asset.common_asset.model.ParameterCount;
import lk.luminex.asset.common_asset.model.TwoDate;
import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.order.entity.Order;
import lk.luminex.asset.payment.entity.enums.PaymentMethod;
import lk.luminex.asset.order.service.OrderService;
import lk.luminex.asset.invoice_ledger.entity.OrderLedger;
import lk.luminex.asset.invoice_ledger.service.OrderLedgerService;
import lk.luminex.asset.item.entity.Item;
import lk.luminex.asset.payment.entity.Payment;
import lk.luminex.asset.payment.service.PaymentService;
import lk.luminex.asset.user_management.user.service.UserService;
import lk.luminex.util.service.DateTimeAgeService;
import lk.luminex.util.service.OperatorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/report" )
public class ReportController {
  private final PaymentService paymentService;
  private final OrderService orderService;
  private final OperatorService operatorService;
  private final DateTimeAgeService dateTimeAgeService;
  private final UserService userService;
  private final OrderLedgerService orderLedgerService;

  public ReportController(PaymentService paymentService, OrderService orderService, OperatorService operatorService, DateTimeAgeService dateTimeAgeService, UserService userService, OrderLedgerService orderLedgerService) {
    this.paymentService = paymentService;
    this.orderService = orderService;
    this.operatorService = operatorService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.userService = userService;
    this.orderLedgerService = orderLedgerService;
  }

  private String commonAll(List< Payment > payments, List< Order > orders, Model model, String message,
                           LocalDateTime startDateTime, LocalDateTime endDateTime) {
    //according to payment type -> invoice
    commonOrders(orders, model);
    //according to payment type -> payment
    commonPayment(payments, model);
    // invoice count by cashier
    commonPerCashier(orders, model);
    // payment count by account department
    commonPerAccountUser(payments, model);
    // item count according to item
    commonPerItem(startDateTime, endDateTime, model);

    model.addAttribute("message", message);
    return "report/paymentAndIncomeReport";
  }

  @GetMapping( "/manager" )
  public String getAllOrderAndPayment(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString();
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);

    return commonAll(paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime),
                     orderService.findByCreatedAtIsBetween(startDateTime, endDateTime), model, message,
                     startDateTime, endDateTime);

  }

  @PostMapping( "/manager/search" )
  public String getAllOrderAndPaymentBetweenTwoDate(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString();
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    return commonAll(paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime),
                     orderService.findByCreatedAtIsBetween(startDateTime, endDateTime), model, message,
                     startDateTime, endDateTime);
  }
  private void commonOrders(List< Order > orders, Model model) {
   /* // invoice count
    int invoiceTotalCount = invoices.size();
    model.addAttribute("invoiceTotalCount", invoiceTotalCount);
    //|-> card
    List< Order > invoiceCards =
        invoices.stream().filter(x -> x.getPaymentMethod().equals(PaymentMethod.CREDIT)).collect(Collectors.toList());
    int invoiceCardCount = invoiceCards.size();
    AtomicReference< BigDecimal > invoiceCardAmount = new AtomicReference<>(BigDecimal.ZERO);
    invoiceCards.forEach(x -> {
      BigDecimal addAmount = operatorService.addition(invoiceCardAmount.get(), x.getTotalAmount());
      invoiceCardAmount.set(addAmount);
    });
    model.addAttribute("invoiceCardCount", invoiceCardCount);
    model.addAttribute("invoiceCardAmount", invoiceCardAmount.get());
    //|-> cash
    List< Order > invoiceCash =
        invoices.stream().filter(x -> x.getPaymentMethod().equals(PaymentMethod.CASH)).collect(Collectors.toList());
    int invoiceCashCount = invoiceCash.size();
    AtomicReference< BigDecimal > invoiceCashAmount = new AtomicReference<>(BigDecimal.ZERO);
    invoiceCash.forEach(x -> {
      BigDecimal addAmount = operatorService.addition(invoiceCashAmount.get(), x.getTotalAmount());
      invoiceCashAmount.set(addAmount);
    });
    model.addAttribute("invoiceCashCount", invoiceCashCount);
    model.addAttribute("invoiceCashAmount", invoiceCashAmount.get());*/

  }

  @GetMapping( "/cashier" )
  public String getCashierToday(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString() + " and \n congratulation all are done by " +
        "you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);
    commonOrders(orderService.findByCreatedAtIsBetweenAndCreatedBy(startDateTime, endDateTime,
                                                                     SecurityContextHolder.getContext().getAuthentication().getName()), model);
    model.addAttribute("message", message);
    return "report/cashierReport";
  }

  @PostMapping( "/cashier/search" )
  public String getCashierSearch(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString() + " and \n congratulation all are done by you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    commonOrders(orderService.findByCreatedAtIsBetweenAndCreatedBy(startDateTime, endDateTime,
                                                                     SecurityContextHolder.getContext().getAuthentication().getName()), model);
    model.addAttribute("message", message);
    return "report/cashierReport";
  }

  private void commonPayment(List< Payment > payments, Model model) {
    // payment count
    int paymentTotalCount = payments.size();
    model.addAttribute("paymentTotalCount", paymentTotalCount);
    //|-> card
    List< Payment > paymentCards =
        payments.stream().filter(x -> x.getPaymentMethod().equals(PaymentMethod.CREDIT)).collect(Collectors.toList());
    int paymentCardCount = paymentCards.size();
    AtomicReference< BigDecimal > paymentCardAmount = new AtomicReference<>(BigDecimal.ZERO);
    paymentCards.forEach(x -> {
      BigDecimal addAmount = operatorService.addition(paymentCardAmount.get(), x.getAmount());
      paymentCardAmount.set(addAmount);
    });
    model.addAttribute("paymentCardCount", paymentCardCount);
    model.addAttribute("paymentCardAmount", paymentCardAmount.get());
    //|-> cash
    List< Payment > paymentCash =
        payments.stream().filter(x -> x.getPaymentMethod().equals(PaymentMethod.CASH)).collect(Collectors.toList());
    int paymentCashCount = paymentCash.size();
    AtomicReference< BigDecimal > paymentCashAmount = new AtomicReference<>(BigDecimal.ZERO);
    paymentCash.forEach(x -> {
      BigDecimal addAmount = operatorService.addition(paymentCashAmount.get(), x.getAmount());
      paymentCashAmount.set(addAmount);
    });
    model.addAttribute("paymentCashCount", paymentCashCount);
    model.addAttribute("paymentCardAmount", paymentCashAmount.get());

  }

  @GetMapping( "/payment" )
  public String getPaymentToday(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString() + " and \n congratulation all are done by " +
        "you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);
    commonPayment(paymentService.findByCreatedAtIsBetweenAndCreatedBy(startDateTime, endDateTime,
                                                                      SecurityContextHolder.getContext().getAuthentication().getName()), model);
    model.addAttribute("message", message);
    return "report/paymentReport";
  }

  @PostMapping( "/payment/search" )
  public String getPaymentSearch(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString() + " and \n congratulation all are done by you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    commonPayment(paymentService.findByCreatedAtIsBetweenAndCreatedBy(startDateTime, endDateTime,
                                                                      SecurityContextHolder.getContext().getAuthentication().getName()), model);
    model.addAttribute("message", message);
    return "report/paymentReport";
  }

  private void commonPerCashier(List< Order > orders, Model model) {
    List< NameCount > invoiceByCashierAndTotalAmount = new ArrayList<>();
//name, count, total
    HashSet< String > createdByAll = new HashSet<>();
    orders.forEach(x -> createdByAll.add(x.getCreatedBy()));

    createdByAll.forEach(x -> {
      NameCount nameCount = new NameCount();
      Employee employee = userService.findByUserName(x).getEmployee();
      nameCount.setName(employee.getTitle().getTitle() + " " + employee.getName());
      AtomicReference< BigDecimal > cashierTotalCount = new AtomicReference<>(BigDecimal.ZERO);
      List< Order > cashierOrder =
          orders.stream().filter(a -> a.getCreatedBy().equals(x)).collect(Collectors.toList());
      nameCount.setCount(cashierOrder.size());
      cashierOrder.forEach(a -> {
        BigDecimal addAmount = operatorService.addition(cashierTotalCount.get(), a.getTotalAmount());
        cashierTotalCount.set(addAmount);
      });
      nameCount.setTotal(cashierTotalCount.get());
      invoiceByCashierAndTotalAmount.add(nameCount);
    });
    model.addAttribute("invoiceByCashierAndTotalAmount", invoiceByCashierAndTotalAmount);
  }

  @GetMapping( "/perCashier" )
  public String perCashierToday(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString();
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);
    commonPerCashier(orderService.findByCreatedAtIsBetween(startDateTime, endDateTime), model);
    model.addAttribute("message", message);
    return "report/perCashierReport";
  }

  @PostMapping( "/perCashier/search" )
  public String getPerCashierSearch(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString() + " and \n congratulation all are done by you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    commonPerCashier(orderService.findByCreatedAtIsBetween(startDateTime, endDateTime), model);
    model.addAttribute("message", message);
    return "report/perCashierReport";
  }

  private void commonPerAccountUser(List< Payment > payments, Model model) {
    List< NameCount > paymentByUserAndTotalAmount = new ArrayList<>();
//name, count, total
    HashSet< String > createdByAllPayment = new HashSet<>();
    payments.forEach(x -> createdByAllPayment.add(x.getCreatedBy()));

    createdByAllPayment.forEach(x -> {
      NameCount nameCount = new NameCount();
      Employee employee = userService.findByUserName(x).getEmployee();
      nameCount.setName(employee.getTitle().getTitle() + " " + employee.getName());
      List< BigDecimal > userTotalCount = new ArrayList<>();
      List< Payment > paymentUser =
          payments.stream().filter(a -> a.getCreatedBy().equals(x)).collect(Collectors.toList());
      nameCount.setCount(paymentUser.size());
      paymentUser.forEach(a -> {
        userTotalCount.add(a.getAmount());
      });
      nameCount.setTotal(userTotalCount.stream().reduce(BigDecimal.ZERO,BigDecimal::add));
      paymentByUserAndTotalAmount.add(nameCount);
    });

    model.addAttribute("paymentByUserAndTotalAmount", paymentByUserAndTotalAmount);

  }

  @GetMapping( "/perAccount" )
  public String perAccountToday(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString();
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);
    commonPerAccountUser(paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime), model);
    model.addAttribute("message", message);
    return "report/perAccountReport";
  }

  @PostMapping( "/perAccount/search" )
  public String getPerAccountSearch(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString() + " and \n congratulation all are done by you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    commonPerAccountUser(paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime), model);
    model.addAttribute("message", message);
    return "report/perAccountReport";
  }

  private void commonPerItem(LocalDateTime startDateTime, LocalDateTime endDateTime, Model model) {
    HashSet< Item > invoiceItems = new HashSet<>();

    List< ParameterCount > itemNameAndItemCount = new ArrayList<>();

    List< OrderLedger > orderLedgers = orderLedgerService.findByCreatedAtIsBetween(startDateTime, endDateTime);
    orderLedgers.forEach(x -> invoiceItems.add(x.getLedger().getItem()));

    invoiceItems.forEach(x -> {
      ParameterCount parameterCount = new ParameterCount();
      parameterCount.setName(x.getName());
      parameterCount.setCount((int) orderLedgers
          .stream()
          .filter(a -> a.getLedger().getItem().equals(x))
          .count());
      itemNameAndItemCount.add(parameterCount);
    });
    model.addAttribute("itemNameAndItemCount", itemNameAndItemCount);

  }

  @GetMapping( "/perItem" )
  public String perItemToday(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate.toString();
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(localDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(localDate);
    commonPerItem(startDateTime, endDateTime, model);
    model.addAttribute("message", message);
    return "report/perItemReport";
  }

  @PostMapping( "/perItem/search" )
  public String getPerItemSearch(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {
    String message =
        "This report is between from " + twoDate.getStartDate().toString() + " to " + twoDate.getEndDate().toString() + " and \n congratulation all are done by you.";
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    commonPerItem(startDateTime, endDateTime, model);
    model.addAttribute("message", message);
    return "report/perItemReport";
  }

}
