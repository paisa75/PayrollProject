package salary.payment.model.dto;

import java.math.BigDecimal;

public class InventoryFileDto {
    private String depositNo;
    private BigDecimal amount;

    public InventoryFileDto() {
    }

    public InventoryFileDto(String depositNo, BigDecimal amount) {
        this.depositNo = depositNo;
        this.amount = amount;
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

    @Override
    public String toString() {
<<<<<<< HEAD
<<<<<<< HEAD
        return depositNo + "\t" + amount;
=======
        return  depositNo +"\t" + amount ;
>>>>>>> origin/main
=======
        return depositNo + "\t" + amount;
>>>>>>> origin/main
    }
}
