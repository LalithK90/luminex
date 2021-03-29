package lk.luminex.asset.project_order.service;

import lk.luminex.asset.project_order.dao.ProjectOrderDao;
import lk.luminex.asset.project_order.entity.ProjectOrder;
import lk.luminex.asset.project_order.entity.enums.OrderState;
import lk.luminex.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectOrderService implements AbstractService< ProjectOrder, Integer > {
  private final ProjectOrderDao projectOrderDao;

  public ProjectOrderService(ProjectOrderDao projectOrderDao) {
    this.projectOrderDao = projectOrderDao;
  }


  public List< ProjectOrder > findAll() {
    return projectOrderDao.findAll();
  }

  public ProjectOrder findById(Integer id) {
    return projectOrderDao.getOne(id);
  }

  public ProjectOrder persist(ProjectOrder projectOrder) {
    return projectOrderDao.save(projectOrder);
  }

  public boolean delete(Integer id) {
    ProjectOrder projectOrder = projectOrderDao.getOne(id);
    projectOrder.setOrderState(OrderState.CANCELED);
    projectOrderDao.save(projectOrder);
    return false;
  }

  public List< ProjectOrder > search(ProjectOrder projectOrder) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< ProjectOrder > orderExample = Example.of(projectOrder, matcher);
    return projectOrderDao.findAll(orderExample);

  }

  public List< ProjectOrder > findByCreatedAtIsBetween(LocalDateTime from, LocalDateTime to) {
    return projectOrderDao.findByCreatedAtIsBetween(from, to);
  }

  public ProjectOrder findByLastOrder() {
    return projectOrderDao.findFirstByOrderByIdDesc();
  }

  public List< ProjectOrder > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime from, LocalDateTime to, String userName) {
    return projectOrderDao.findByCreatedAtIsBetweenAndCreatedBy(from, to, userName);
  }

/*  public ByteArrayInputStream createPDF(Integer id) throws DocumentException {
    Order order = orderDao.getOne(id);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4, 15, 15, 45, 30);
    PdfWriter.getInstance(document, out);
    document.open();

    Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
    Font secondaryFont = FontFactory.getFont("Arial", 8, BaseColor.BLACK);
    Font highLiltedFont = FontFactory.getFont("Arial", 8, BaseColor.BLACK);


    Paragraph paragraph = new Paragraph("Samarasingher Super Center \n \t\t Welipillewa\n \t Kadawatha \n", mainFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setIndentationLeft(50);
    paragraph.setIndentationRight(50);
    paragraph.setSpacingAfter(10);
    document.add(paragraph);

//customer details and order main details
    float[] columnWidths = {200f, 200f};//column amount{column 1 , column 2 }
    PdfPTable mainTable = new PdfPTable(columnWidths);
    // add cell to table
    PdfPCell cell = new PdfPCell(new Phrase("Date : \t" + order.getCreatedAt().format(DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss")), secondaryFont));
    pdfCellBodyCommonStyle(cell);
    mainTable.addCell(cell);

    PdfPCell cell1 = new PdfPCell(new Phrase("Order Number : " + order.getCode(), secondaryFont));
    pdfCellBodyCommonStyle(cell1);
    mainTable.addCell(cell1);

    PdfPCell cell2;
    if ( order.getProject() != null ) {
      cell2 =
          new PdfPCell(new Phrase("Name : " + order.getProject().getName(), secondaryFont));
    } else {
      cell2 = new PdfPCell(new Phrase("Name : Anonymous Customer ", secondaryFont));
    }
    pdfCellBodyCommonStyle(cell2);
    mainTable.addCell(cell2);

    document.add(mainTable);

    PdfPTable ledgerItemDisplay = new PdfPTable(5);//column amount
    ledgerItemDisplay.setWidthPercentage(100);
    ledgerItemDisplay.setSpacingBefore(10f);
    ledgerItemDisplay.setSpacingAfter(10);

    Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
    ledgerItemDisplay.setWidths(columnWidths);

    PdfPCell indexHeader = new PdfPCell(new Paragraph("Index", tableHeader));
    pdfCellHeaderCommonStyle(indexHeader);
    ledgerItemDisplay.addCell(indexHeader);

    PdfPCell itemNameHeader = new PdfPCell(new Paragraph("Item Name", tableHeader));
    pdfCellHeaderCommonStyle(itemNameHeader);
    ledgerItemDisplay.addCell(itemNameHeader);

    PdfPCell unitPriceHeader = new PdfPCell(new Paragraph("Unit Price", tableHeader));
    pdfCellHeaderCommonStyle(unitPriceHeader);
    ledgerItemDisplay.addCell(unitPriceHeader);

    PdfPCell quantityHeader = new PdfPCell(new Paragraph("Quantity", tableHeader));
    pdfCellHeaderCommonStyle(quantityHeader);
    ledgerItemDisplay.addCell(quantityHeader);

    PdfPCell lineTotalHeader = new PdfPCell(new Paragraph("Line Total", tableHeader));
    pdfCellHeaderCommonStyle(lineTotalHeader);
    ledgerItemDisplay.addCell(lineTotalHeader);

    for ( int i = 0; i < order.getOrderLedgers().size(); i++ ) {
      PdfPCell index = new PdfPCell(new Paragraph(Integer.toString(i), tableHeader));
      pdfCellBodyCommonStyle(index);
      ledgerItemDisplay.addCell(index);

      PdfPCell itemName = new PdfPCell(new Paragraph(order.getOrderLedgers().get(i).getLedger().getItem().getName(), tableHeader));
      pdfCellBodyCommonStyle(itemName);
      ledgerItemDisplay.addCell(itemName);

      PdfPCell unitPrice = new PdfPCell(new Paragraph(order.getOrderLedgers().get(i).getLedger().getSellPrice().toString(), tableHeader));
      pdfCellBodyCommonStyle(unitPrice);
      ledgerItemDisplay.addCell(unitPrice);

      PdfPCell quantity = new PdfPCell(new Paragraph(order.getOrderLedgers().get(i).getQuantity(), tableHeader));
      pdfCellBodyCommonStyle(quantity);
      ledgerItemDisplay.addCell(quantity);

      PdfPCell lineTotal = new PdfPCell(new Paragraph(order.getOrderLedgers().get(i).getLineTotal().toString(), tableHeader));
      pdfCellBodyCommonStyle(lineTotal);
      ledgerItemDisplay.addCell(lineTotal);
    }

    document.add(ledgerItemDisplay);

    PdfPTable orderTable = new PdfPTable(new float[]{3f, 1f});

    PdfPCell totalAmount = new PdfPCell(new Phrase("\nTotal Amount(Rs.) : ", secondaryFont));
    commonStyleForPdfPCellLastOne(totalAmount);
    orderTable.addCell(totalAmount);

    PdfPCell totalAmountRs = new PdfPCell(new Phrase("---------------\n" + order.getTotalPrice().setScale(2, BigDecimal.ROUND_CEILING).toString(), secondaryFont));
    commonStyleForPdfPCellLastOne(totalAmountRs);
    orderTable.addCell(totalAmountRs);

    PdfPCell paymentMethodOnBill = new PdfPCell(new Phrase("\nPayment Method : ", secondaryFont));
    commonStyleForPdfPCellLastOne(paymentMethodOnBill);
    orderTable.addCell(paymentMethodOnBill);

    PdfPCell paymentMethodOnBillState = new PdfPCell(new Phrase("========\n" + order.getPaymentMethod().getPaymentMethod(), secondaryFont));
    commonStyleForPdfPCellLastOne(paymentMethodOnBillState);
    orderTable.addCell(paymentMethodOnBillState);

    PdfPCell discountRadioAndAmount = new PdfPCell(new Phrase("Discount ( " + order.getDiscountRatio().getAmount() + "% ) (Rs.) : ", secondaryFont));
    commonStyleForPdfPCellLastOne(discountRadioAndAmount);
    orderTable.addCell(discountRadioAndAmount);

    PdfPCell discountRadioAndAmountRs = new PdfPCell(new Phrase(order.getDiscountAmount().toString(), secondaryFont));
    commonStyleForPdfPCellLastOne(discountRadioAndAmountRs);
    orderTable.addCell(discountRadioAndAmountRs);

    PdfPCell amount = new PdfPCell(new Phrase("Amount (Rs.) : ", highLiltedFont));
    commonStyleForPdfPCellLastOne(amount);
    orderTable.addCell(amount);

    PdfPCell amountRs = new PdfPCell(new Phrase(order.getTotalAmount().toString(), highLiltedFont));
    commonStyleForPdfPCellLastOne(amountRs);
    orderTable.addCell(amountRs);


    if (order.getPaymentMethod().equals(PaymentMethod.CASH)) {
      PdfPCell amountTendered = new PdfPCell(new Phrase("Tender Amount (Rs.) : ", secondaryFont));
      commonStyleForPdfPCellLastOne(amountTendered);
      orderTable.addCell(amountTendered);

      PdfPCell amountTenderedRs = new PdfPCell(new Phrase(order.getAmountTendered().toString(), secondaryFont));
      commonStyleForPdfPCellLastOne(amountTenderedRs);
      orderTable.addCell(amountTenderedRs);

      PdfPCell balance = new PdfPCell(new Phrase("Balance (Rs.) : ", highLiltedFont));
      commonStyleForPdfPCellLastOne(balance);
      orderTable.addCell(balance);

      PdfPCell balanceRs = new PdfPCell(new Phrase(order.getBalance().toString(), highLiltedFont));
      commonStyleForPdfPCellLastOne(balanceRs);
      orderTable.addCell(balanceRs);

    } else {
      PdfPCell bank = new PdfPCell(new Phrase("Bank Name : ", secondaryFont));
      commonStyleForPdfPCellLastOne(bank);
      orderTable.addCell(bank);

      PdfPCell bankName = new PdfPCell(new Phrase(order.getBankName(), secondaryFont));
      commonStyleForPdfPCellLastOne(bankName);
      orderTable.addCell(bankName);
    }

    document.add(orderTable);

    Paragraph remarks = new Paragraph("Remarks : " + order.getRemarks(), secondaryFont);
    commonStyleForParagraphTwo(remarks);
    document.add(remarks);

    Paragraph message = new Paragraph("\nWe will not accept return without orderd. \n\n ------------------------------------\n            ( " + order.getCreatedBy() + " )", secondaryFont);
    commonStyleForParagraphTwo(message);
    document.add(message);

    document.close();
    return new ByteArrayInputStream(out.toByteArray());
  }

  private void pdfCellHeaderCommonStyle(PdfPCell pdfPCell) {
    pdfPCell.setBorderColor(BaseColor.BLACK);
    pdfPCell.setPaddingLeft(10);
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setBackgroundColor(BaseColor.DARK_GRAY);
    pdfPCell.setExtraParagraphSpace(5f);
  }

  private void pdfCellBodyCommonStyle(PdfPCell pdfPCell) {
    pdfPCell.setBorderColor(BaseColor.BLACK);
    pdfPCell.setPaddingLeft(10);
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setBackgroundColor(BaseColor.WHITE);
    pdfPCell.setExtraParagraphSpace(5f);
  }

  private void commonStyleForPdfPCellLastOne(PdfPCell pdfPCell) {
    pdfPCell.setBorder(0);
    pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    pdfPCell.setBorderColor(BaseColor.WHITE);
  }

  private void commonStyleForParagraphTwo(Paragraph paragraph) {
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setIndentationLeft(50);
    paragraph.setIndentationRight(50);
  }*/
}
