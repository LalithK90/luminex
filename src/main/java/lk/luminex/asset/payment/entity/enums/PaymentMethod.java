package lk.luminex.asset.payment.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("Cash"),
    CHEQUE("Cheque");
    private final String paymentMethod;
}
