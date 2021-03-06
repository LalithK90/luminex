package lk.luminex.asset.payment.controller;


import lk.luminex.asset.common_asset.model.TwoDate;
import lk.luminex.asset.good_received_note.entity.GoodReceivedNote;
import lk.luminex.asset.good_received_note.entity.enums.GoodReceivedNoteState;
import lk.luminex.asset.good_received_note.service.GoodReceivedNoteService;
import lk.luminex.asset.payment.entity.enums.PaymentMethod;
import lk.luminex.asset.payment.entity.Payment;
import lk.luminex.asset.payment.service.PaymentService;
import lk.luminex.asset.purchase_order.entity.PurchaseOrder;
import lk.luminex.asset.purchase_order.entity.enums.PurchaseOrderStatus;
import lk.luminex.asset.purchase_order.service.PurchaseOrderService;
import lk.luminex.util.service.DateTimeAgeService;
import lk.luminex.util.service.MakeAutoGenerateNumberService;
import lk.luminex.util.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final PurchaseOrderService purchaseOrderService;
    private final OperatorService operatorService;
    private final GoodReceivedNoteService goodReceivedNoteService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final DateTimeAgeService dateTimeAgeService;

    public PaymentController(PaymentService paymentService, PurchaseOrderService purchaseOrderService,
                             OperatorService operatorService, GoodReceivedNoteService goodReceivedNoteService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                             DateTimeAgeService dateTimeAgeService) {
        this.paymentService = paymentService;
        this.purchaseOrderService = purchaseOrderService;
        this.operatorService = operatorService;
        this.goodReceivedNoteService = goodReceivedNoteService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    private String commonMethod(List<PurchaseOrder> purchaseOrdersDB, Model model) {
        if (!purchaseOrdersDB.isEmpty()) {
            //need to pay po
            List<PurchaseOrder> purchaseOrders = new ArrayList<>();

            for (PurchaseOrder purchaseOrder : purchaseOrdersDB) {
                List<Payment> payments = paymentService.findByPurchaseOrder(purchaseOrder);
                if (payments != null) {
                    BigDecimal paidAmount = BigDecimal.ZERO;
                    for (Payment payment : payments) {
                        paidAmount = operatorService.addition(paidAmount, payment.getAmount());
                    }
                    purchaseOrder.setPrice(operatorService.subtraction(purchaseOrder.getPrice(), paidAmount));
                }
                purchaseOrders.add(purchaseOrder);
            }

            model.addAttribute("purchaseOrders", purchaseOrders);
        }
        model.addAttribute("addState", true);
        return "payment/payment";
    }

    @GetMapping
    public String getAllPurchaseOrderToPay(Model model) {
        //find all purchase order to have to pay using purchase order status
        //1. still not processed po 2. partially paid po
        List<PurchaseOrder> purchaseOrdersDB =
                purchaseOrderService.findByPurchaseOrderStatus(PurchaseOrderStatus.NOT_PROCEED);
        return commonMethod(purchaseOrdersDB, model);
    }

    @PostMapping("/search")
    public String getAllPurchaseOrderToPayBetweenTwoDate(@ModelAttribute TwoDate twoDate, Model model) {
        return commonMethod(purchaseOrderService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate()), dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate()))
                , model);
    }

    @GetMapping("/{id}")
    public String addPaymentAmount(@PathVariable("id") Integer id, Model model) {
        //payment need to make
        PurchaseOrder purchaseOrderNeedToPay = purchaseOrderService.findById(id);

        List<Payment> payments =
                paymentService.findByPurchaseOrder(purchaseOrderNeedToPay);

        if (!payments.isEmpty()) {
            List<BigDecimal> paidAmounts = new ArrayList<>();
            for (Payment payment : payments) {
                paidAmounts.add(payment.getAmount());
            }
            purchaseOrderNeedToPay.setNeedToPaid(operatorService.subtraction(purchaseOrderNeedToPay.getPrice(), paidAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add)));
        }
        model.addAttribute("payment", new Payment());
        model.addAttribute("payments", payments);
        model.addAttribute("purchaseOrderNeedToPay", purchaseOrderNeedToPay);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "payment/addPayment";
    }

    @PostMapping
    public String savePayment(@Valid @ModelAttribute Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/payment/".concat(String.valueOf(payment.getPurchaseOrder().getId()));
        }
        if (payment.getId() == null) {
            if (paymentService.lastPayment() == null) {
                //need to generate new one
                payment.setCode("SSMP" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
            } else {
                //if there is customer in db need to get that customer's code and increase its value
                String previousCode = paymentService.lastPayment().getCode().substring(4);
                payment.setCode("SSMP" + makeAutoGenerateNumberService.numberAutoGen(previousCode).toString());
            }
        }

        //1. need to save payment
        Payment paymentDB = paymentService.persist(payment);
        PurchaseOrder purchaseOrder = paymentDB.getPurchaseOrder();
        BigDecimal purchaseOrderPrice = purchaseOrderService.findById(purchaseOrder.getId()).getPrice();
        //2. check po state -> need to finished all payment to change this
        //3. check grn state -> need to finished all payment to change this
        List<Payment> payments = paymentService.findByPurchaseOrder(purchaseOrder);
        if (!payments.isEmpty()) {
            BigDecimal paidAmount = BigDecimal.ZERO;
            for (Payment paymentOne : payments) {
                paidAmount = operatorService.addition(paidAmount, paymentOne.getAmount());
            }
            // if check all paid amount is equal or not purchase order amount

            if (paidAmount.equals(purchaseOrderPrice)) {
                System.out.println("Im here ");
                //change GRN sate
                GoodReceivedNote goodReceivedNote = goodReceivedNoteService.findByPurchaseOrder(purchaseOrder);
                goodReceivedNote.setGoodReceivedNoteState(GoodReceivedNoteState.PAID);
                goodReceivedNoteService.persist(goodReceivedNote);
                //change purchase order status
                PurchaseOrder completedPurchaseOrder = purchaseOrderService.findById(purchaseOrder.getId());
                completedPurchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.COMPLETED);
                purchaseOrderService.persist(completedPurchaseOrder);
            }
        }
        return "redirect:/payment";
    }

    @GetMapping("/allPayment")
    public String allPayment(Model model) {
        return commonPayment(model, paymentService.findAll());
    }

    private String commonPayment(Model model, List<Payment> payment) {
        model.addAttribute("payments", payment);
        return "payment/allPayment";
    }

    @PostMapping("/all/search")
    public String getAllPaymentToPayBetweenTwoDate(@ModelAttribute TwoDate twoDate, Model model) {
        return commonPayment(model,
                paymentService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate()), dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate()))
        );
    }
}
