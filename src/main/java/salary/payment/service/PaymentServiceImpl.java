package salary.payment.service;

import salary.payment.exceptions.InsufficientInventoryException;
import salary.payment.io.InventoryFile;
import salary.payment.io.PaymentFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.dto.TransactionFileDto;
import salary.payment.model.enums.Type;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class PaymentServiceImpl implements PaymentService {

    private final String companyDepositNo = "1.10.100.1";

    @Override
    public void doPayment(List<EmployeeSalary> employeeSalaries) throws ExecutionException, InterruptedException {


        final List<EmployeeSalary> emp = employeeSalaries;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new Runnable() {
            public void run() {

                BigDecimal sum = getAmountSum(emp);

                InventoryFile inventory = new InventoryFile();
                BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);

                if (companyBalance.compareTo(sum) == -1) {
                    System.out.println("Company Balance is not Enough");
                    System.exit(0);
//            throw new InsufficientInventoryException("Inventory is not enough",code);
                }

                PaymentFile paymentFile = new PaymentFile();
                TransactionFile transactionFile = new TransactionFile();




                for (EmployeeSalary employeeSalary : emp) {
                    paymentFile.addToPaymentFile(new PaymentFileDto(employeeSalary.getDepositNo(), employeeSalary.getAmount(),Type.creditor));
                    InventoryFileDto dto = new InventoryFileDto(employeeSalary.getDepositNo(),employeeSalary.getAmount());
                    inventory.replaceSelected(dto);
                    transactionFile.createTransactionFile(new TransactionFileDto(companyDepositNo, employeeSalary.getDepositNo(), employeeSalary.getAmount()));
                }

            }
        });

        executorService.shutdown();
    }


    public BigDecimal getAmountSum(List<EmployeeSalary> employeeSalaries) {
        BigDecimal sum = BigDecimal.ZERO;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            sum = sum.add(employeeSalary.getAmount());
        }
        return sum;
    }
}
