import salary.payment.io.InventoryFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.service.PaymentService;
import salary.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {


    private static String companyDepositNo = "1.10.100.1";

    public static void main(String[] args) {
        InventoryFile inventory = new InventoryFile();
        inventory.createInventoryFile();

        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();
        salary.payment.model.dto.EmployeeSalary salary = new salary.payment.model.dto.EmployeeSalary();
        salary.setId(1);
        salary.setDepositNo("1.20.100.1");
        salary.setAmount(BigDecimal.valueOf(300));

        employeeSalaryList.add(salary);
        salary.payment.model.dto.EmployeeSalary salary2 = new salary.payment.model.dto.EmployeeSalary();
        salary2.setId(2);
        salary2.setDepositNo("1.20.100.2");
        salary2.setAmount(BigDecimal.valueOf(700));
        employeeSalaryList.add(salary2);

            PaymentService paymentService = new PaymentServiceImpl();
            paymentService.doPayment(employeeSalaryList);
            System.out.println("payment Don!!!!");


    }

  /*  public static void doPayment(EmployeeSalary employeeSalary) {

        InventoryFile inventory = new InventoryFile();
       *//* List<salary.payment.model.dto.EmployeeSalary> employeeSalaryList = new ArrayList<>();
        salary.payment.model.dto.EmployeeSalary salary = new salary.payment.model.dto.EmployeeSalary();
        salary.setId(1);
        salary.setDepositNo("1.20.100.1");
        salary.setAmount(BigDecimal.valueOf(300));

        employeeSalaryList.add(salary);
        salary.payment.model.dto.EmployeeSalary salary2 = new salary.payment.model.dto.EmployeeSalary();
        salary2.setId(2);
        salary2.setDepositNo("1.20.100.2");
        salary2.setAmount(BigDecimal.valueOf(700));
        employeeSalaryList.add(salary2);*//*

        BigDecimal sum = employeeSalary.getAmount();
        *//*BigDecimal sum = BigDecimal.ZERO;
        for (salary.payment.model.dto.EmployeeSalary employeeSalary : employeeSalaryList) {
            sum = sum.add(employeeSalary.getAmount());
        }*//*
        //TODO  read company Balance from file


        BigDecimal companyBalance = BigDecimal.ZERO;

        Path file = Paths.get("inventoryFile.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(companyDepositNo)) {
                    String amount = line.replace(companyDepositNo, "");
                    amount = amount.replace("\t", "");
                    if (amount.equals("0"))
                        companyBalance = BigDecimal.ZERO;
                    else
                        companyBalance = new BigDecimal(amount);
                    System.out.println("Company Balance is :" + companyBalance);
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        if (companyBalance.compareTo(sum) == -1) {
            System.out.println("Company Balance is not Enough");
            System.exit(0);
        }
        PaymentFile paymentFile = new PaymentFile();
        TransactionFile transactionFile = new TransactionFile();

        paymentFile.addToPaymentFile(Type.debtor, companyDepositNo, sum);
        paymentFile.addToPaymentFile(Type.creditor, employeeSalary.getDepositNo(), employeeSalary.getAmount());


        //for (salary.payment.model.dto.EmployeeSalary employeeSalary : employeeSalaryList) {

        inventory.replaceSelected(companyDepositNo, companyBalance.subtract(sum));
        inventory.replaceSelected(employeeSalary.getDepositNo(), employeeSalary.getAmount());
        //}
        transactionFile.createTransactionFile(companyDepositNo, employeeSalary.getDepositNo(), employeeSalary.getAmount());
    }*/

}

