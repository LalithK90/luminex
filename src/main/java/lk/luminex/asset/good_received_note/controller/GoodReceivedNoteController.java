package lk.luminex.asset.good_received_note.controller;

import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.good_received_note.entity.GoodReceivedNote;
import lk.luminex.asset.good_received_note.entity.enums.GoodReceivedNoteState;
import lk.luminex.asset.good_received_note.service.GoodReceivedNoteService;
import lk.luminex.asset.ledger.entity.Ledger;
import lk.luminex.asset.ledger.service.LedgerService;
import lk.luminex.asset.purchase_order.entity.PurchaseOrder;
import lk.luminex.asset.purchase_order.entity.enums.PurchaseOrderStatus;
import lk.luminex.asset.purchase_order.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/goodReceivedNote" )
public class GoodReceivedNoteController {
  private final GoodReceivedNoteService goodReceivedNoteService;
  private final PurchaseOrderService purchaseOrderService;
  private final LedgerService ledgerService;

  public GoodReceivedNoteController(GoodReceivedNoteService goodReceivedNoteService,
                                    PurchaseOrderService purchaseOrderService, LedgerService ledgerService) {
    this.goodReceivedNoteService = goodReceivedNoteService;
    this.purchaseOrderService = purchaseOrderService;
    this.ledgerService = ledgerService;
  }


  @GetMapping
  public String notCompleteAll(Model model) {
    model.addAttribute("notCompleteAll",
                       purchaseOrderService.findByPurchaseOrderStatus(PurchaseOrderStatus.NOT_COMPLETED));
    return "goodReceivedNote/goodReceivedNote";
  }

  @GetMapping( "/{id}" )
  public String grnAdd(@PathVariable Integer id, Model model) {
    PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);
    model.addAttribute("purchaseOrderDetail", purchaseOrder);
    model.addAttribute("supplier", purchaseOrder.getSupplier());
    model.addAttribute("goodReceivedNote", new GoodReceivedNote());
    return "goodReceivedNote/addGoodReceivedNote";
  }

  @PostMapping( "/received" )
  public String saveGRN(@Valid @ModelAttribute GoodReceivedNote goodReceivedNote, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/goodReceivedNote/".concat(String.valueOf(goodReceivedNote.getPurchaseOrder().getId()));
    }
//New Leger add to add system as new item on ledger
    List< Ledger > ledgers = new ArrayList<>();
    for ( Ledger ledger : goodReceivedNote.getLedgers() ) {
      Ledger ledgerDB = ledgerService.findByItem(ledger.getItem());
//if there is on value in ledger need to update it
      if ( ledgerDB != null ) {
        ledgerDB.setQuantity(ledgerDB.getQuantity() + ledger.getQuantity());
        ledgerService.persist(ledgerDB);
        continue;
      }
      ledger.setGoodReceivedNote(goodReceivedNote);
      ledger.setLiveDead(LiveDead.ACTIVE);
      ledgers.add(ledger);
    }
    goodReceivedNote.setGoodReceivedNoteState(GoodReceivedNoteState.NOT_PAID);
    goodReceivedNote.setLedgers(ledgers);

    PurchaseOrder purchaseOrder = purchaseOrderService.findById(goodReceivedNote.getPurchaseOrder().getId());
    purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.NOT_PROCEED);

    purchaseOrderService.persist(purchaseOrder);
    goodReceivedNoteService.persist(goodReceivedNote);
    return "redirect:/goodReceivedNote";
  }

}
