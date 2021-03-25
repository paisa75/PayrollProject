package salary.payment.thread;

import salary.payment.io.InventoryFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.TransactionFileDto;
import salary.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentThread implements Runnable {

    public void doPayment(final String companyDeposit, final List<EmployeeSalary> employeeSalaries) {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        final InventoryFile inventory = new InventoryFile();
        final BigDecimal sum = paymentService.getAmountSum(employeeSalaries);
        final BigDecimal companyBalance = inventory.getDepositBalance(companyDeposit);

        ExecutorService executorService =  Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                inventory.updateBalance(new InventoryFileDto(companyDeposit, companyBalance.subtract(sum)));
                TransactionFile transactionFile = new TransactionFile();
                for (EmployeeSalary employeeSalary : employeeSalaries) {
                    InventoryFileDto item = new InventoryFileDto(employeeSalary.getDepositNo(), employeeSalary.getAmount());
                    transactionFile.createTransactionFile(new TransactionFileDto(companyDeposit, employeeSalary.getDepositNo(), employeeSalary.getAmount()));
                    inventory.updateBalance(item);
                }
                ///implement
            }
        });

    }


    @Override
    public void run() {

    }
}
