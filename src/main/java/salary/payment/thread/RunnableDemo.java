package salary.payment.thread;

import org.apache.log4j.Logger;
import salary.payment.io.InventoryFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.TransactionFileDto;
import salary.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class RunnableDemo implements Runnable {
    private static final Logger logger = Logger.getLogger("Main.class");

    private String companyDeposit;
    private List<EmployeeSalary> employeeSalaries;

    public RunnableDemo(final String companyDeposit, final List<EmployeeSalary> employeeSalaries) {
        this.companyDeposit = companyDeposit;
        this.employeeSalaries = employeeSalaries;
    }

    @Override
    public void run() {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        final InventoryFile inventory = new InventoryFile();
        final BigDecimal sum = paymentService.getAmountSum(employeeSalaries);
        final BigDecimal companyBalance = inventory.getDepositBalance(companyDeposit);

        inventory.updateBalance(new InventoryFileDto(companyDeposit, companyBalance.subtract(sum)));
        TransactionFile transactionFile = new TransactionFile();
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            InventoryFileDto item = new InventoryFileDto(employeeSalary.getDepositNo(), employeeSalary.getAmount());
            transactionFile.createTransactionFile(new TransactionFileDto(companyDeposit, employeeSalary.getDepositNo(), employeeSalary.getAmount()));
            inventory.updateBalance(item);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
