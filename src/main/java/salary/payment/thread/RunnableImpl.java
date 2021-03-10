package salary.payment.thread;

import org.apache.log4j.Logger;
import salary.payment.io.InventoryFile;
import salary.payment.io.PaymentFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.TransactionFileDto;
import salary.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableImpl implements Runnable {
    private static final Logger logger = Logger.getLogger("Main.class");

    ExecutorService service = Executors.newFixedThreadPool(10);

    @Override
    public void run() {
        InventoryFile inventory = new InventoryFile();
        PaymentFile paymentFile = new PaymentFile();
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();
        List<EmployeeSalary> paymentList = paymentFile.readPaymentFile();
        String companyDepositNo = "1.10.100.1";
        BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);
        BigDecimal sum = paymentService.getAmountSum(employeeSalaryList);
        inventory.updateBalance(new InventoryFileDto("1.10.100.1", companyBalance.subtract(sum)));
        TransactionFile transactionFile = new TransactionFile();
        for (EmployeeSalary employeeSalary : paymentList) {
            InventoryFileDto item = new InventoryFileDto(employeeSalary.getDepositNo(), employeeSalary.getAmount());
            transactionFile.createTransactionFile(new TransactionFileDto(companyDepositNo, employeeSalary.getDepositNo(), employeeSalary.getAmount()));
            inventory.updateBalance(item);
        }

        logger.debug("*********************** payment Don!!!!");

    }
}
