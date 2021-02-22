package salary.payment.model.dto;

import salary.payment.model.enums.Type;

import java.math.BigDecimal;

public class PaymentFileDto {

    private String depositNo;
    private BigDecimal amount;
<<<<<<< HEAD
    private Type type;
=======
    private Type type ;
>>>>>>> origin/main

    public PaymentFileDto() {
    }

    public PaymentFileDto(String depositNo, BigDecimal amount, Type type) {
        this.depositNo = depositNo;
        this.amount = amount;
        this.type = type;
    }

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return type.name() + "\t" + depositNo + "\t" + amount;
=======
        return  type.name() + "\t" + depositNo + "\t" + amount ;
>>>>>>> origin/main


    }
}
