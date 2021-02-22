package salary.payment.service;

<<<<<<< HEAD
import org.apache.log4j.Logger;
=======
>>>>>>> origin/main
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
<<<<<<< HEAD
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final String companyDepositNo = "1.10.100.1";

    @Override
    public void doPayment(List<EmployeeSalary> employeeSalaries) {

        BigDecimal sum = getAmountSum(employeeSalaries);

        InventoryFile inventory = new InventoryFile();
        BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);

        if (companyBalance.compareTo(sum) == -1) {
            logger.debug("*********************** Company Balance is not Enough");
            System.exit(0);
            try {
                throw new InsufficientInventoryException("Inventory is not enough",22);
            } catch (InsufficientInventoryException e) {
                e.printStackTrace();
            }
        }

        PaymentFile paymentFile = new PaymentFile();
        TransactionFile transactionFile = new TransactionFile();

        paymentFile.addToPaymentFile(new PaymentFileDto(companyDepositNo, sum ,Type.debtor));
        inventory.replaceSelected(new InventoryFileDto(companyDepositNo, companyBalance.subtract(sum)));


        for (EmployeeSalary employeeSalary : employeeSalaries) {
            paymentFile.addToPaymentFile(new PaymentFileDto(employeeSalary.getDepositNo(), employeeSalary.getAmount(),Type.creditor));
            InventoryFileDto dto = new InventoryFileDto(employeeSalary.getDepositNo(),employeeSalary.getAmount());
            inventory.replaceSelected(dto);
            transactionFile.createTransactionFile(new TransactionFileDto(companyDepositNo, employeeSalary.getDepositNo(), employeeSalary.getAmount()));
        }
=======
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
>>>>>>> origin/main
    }


    public BigDecimal getAmountSum(List<EmployeeSalary> employeeSalaries) {
        BigDecimal sum = BigDecimal.ZERO;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            sum = sum.add(employeeSalary.getAmount());
        }
        return sum;
    }
}
