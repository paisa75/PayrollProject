package salary.payment.service;

import salary.payment.io.InventoryFile;
import salary.payment.io.PaymentFile;
import salary.payment.io.TransactionFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.enums.Type;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final String companyDepositNo = "1.10.100.1";

    @Override
    public void doPayment(List<EmployeeSalary> employeeSalaries) {

        BigDecimal sum = getAmountSum(employeeSalaries);

        InventoryFile inventory = new InventoryFile();
        BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);

        if (companyBalance.compareTo(sum) == -1) {
            System.out.println("Company Balance is not Enough");
            System.exit(0);
        }

        PaymentFile paymentFile = new PaymentFile();
        TransactionFile transactionFile = new TransactionFile();

        paymentFile.addToPaymentFile(Type.debtor, companyDepositNo, sum);
        inventory.replaceSelected(new InventoryFileDto(companyDepositNo, companyBalance.subtract(sum)));


        for (EmployeeSalary employeeSalary : employeeSalaries) {
            paymentFile.addToPaymentFile(Type.creditor, employeeSalary.getDepositNo(), employeeSalary.getAmount());
            InventoryFileDto dto = new InventoryFileDto(employeeSalary.getDepositNo(),employeeSalary.getAmount());
            inventory.replaceSelected(dto);
            transactionFile.createTransactionFile(companyDepositNo, employeeSalary.getDepositNo(), employeeSalary.getAmount());
        }
    }


    public BigDecimal getAmountSum(List<EmployeeSalary> employeeSalaries) {
        BigDecimal sum = BigDecimal.ZERO;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            sum = sum.add(employeeSalary.getAmount());
        }
        return sum;
    }
}
