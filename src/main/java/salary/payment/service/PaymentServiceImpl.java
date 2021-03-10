package salary.payment.service;

import org.apache.log4j.Logger;
import salary.payment.exceptions.InsufficientInventoryException;
import salary.payment.model.dto.EmployeeSalary;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl {
    private final Logger logger = Logger.getLogger(this.getClass());

    public void checkInventory(List<EmployeeSalary> employeeSalaries, BigDecimal companyBalance, BigDecimal sum) {
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


    public BigDecimal getAmountSum(List<EmployeeSalary> employeeSalaries) {
        BigDecimal sum = BigDecimal.ZERO;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            sum = sum.add(employeeSalary.getAmount());
        }
        return sum;
    }


}
