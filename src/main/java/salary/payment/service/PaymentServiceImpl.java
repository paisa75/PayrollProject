package salary.payment.service;

import org.apache.log4j.Logger;
import salary.payment.exceptions.InsufficientInventoryException;
import salary.payment.model.dto.PaymentFileDto;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl {
    private final Logger logger = Logger.getLogger(this.getClass());

    public void checkInventory(List<PaymentFileDto> paymentFileDtos, BigDecimal companyBalance, BigDecimal sum) {
        if (companyBalance.compareTo(sum) == -1) {
            System.out.println("*********************** Company Balance is not Enough");
            System.exit(0);
            try {
                throw new InsufficientInventoryException("Inventory is not enough", 22);
            } catch (InsufficientInventoryException e) {
                e.printStackTrace();
            }
        }
    }

    public BigDecimal getAmountSum(List<PaymentFileDto> paymentFileDtos) {
        BigDecimal sum = BigDecimal.ZERO;
        for (PaymentFileDto paymentFileDto : paymentFileDtos) {
            sum = sum.add(paymentFileDto.getAmount());
        }
        return sum;
    }
}
