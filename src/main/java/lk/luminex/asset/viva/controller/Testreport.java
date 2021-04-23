package lk.luminex.asset.viva.controller;


import lk.luminex.asset.ledger.service.LedgerService;
import lk.luminex.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/testgetapi" )
public class Testreport {

    private final LedgerService ledgerService;
    private final DateTimeAgeService dateTimeAgeService;

    public Testreport(LedgerService ledgerService, DateTimeAgeService dateTimeAgeService) {
        this.ledgerService = ledgerService;
        this.dateTimeAgeService = dateTimeAgeService;
    }
}
