package salary.payment.thread;

import org.apache.log4j.Logger;
import salary.payment.io.InventoryFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.dto.TransactionFileDto;
import salary.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class PaymentThread implements Runnable {
    private static final Logger logger = Logger.getLogger("Main.class");

    private String companyDeposit;
    private List<PaymentFileDto> paymentFileDtos;

    public PaymentThread(final String companyDeposit, final List<PaymentFileDto> paymentFileDtos) {
        this.companyDeposit = companyDeposit;
        this.paymentFileDtos = paymentFileDtos;
    }

    @Override
    public void run() {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        final InventoryFile inventory = new InventoryFile();
        final BigDecimal sum = paymentService.getAmountSum(paymentFileDtos);
        final BigDecimal companyBalance = inventory.getDepositBalance(companyDeposit);

        inventory.updateBalance(new InventoryFileDto(companyDeposit, companyBalance.subtract(sum)));
        TransactionFile transactionFile = new TransactionFile();
        for (PaymentFileDto paymentFileDto : paymentFileDtos) {
            InventoryFileDto item = new InventoryFileDto(paymentFileDto.getDepositNo(), paymentFileDto.getAmount());
            transactionFile.createTransactionFile(new TransactionFileDto(companyDeposit, paymentFileDto.getDepositNo(), paymentFileDto.getAmount()));
            inventory.updateBalance(item);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
