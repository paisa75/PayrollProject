import org.apache.log4j.Logger;
import salary.payment.io.InventoryFile;
import salary.payment.io.PaymentFile;
import salary.payment.model.dto.EmployeeSalary;
import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.enums.Type;
import salary.payment.service.PaymentServiceImpl;
//import salary.payment.thread.PaymentThread;
import salary.payment.thread.RunnableDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger logger = Logger.getLogger("Main.class");

    public static void main(String[] args) throws Exception {
        logger.debug("*********************** main class started");
        //////////////////////// -1-  ////////////////////////////


        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            EmployeeSalary employeeSalary = new EmployeeSalary();
            employeeSalary.setId(i);
            employeeSalary.setDepositNo("1.20.100." + i);
            if (i % 2 == 0)
                employeeSalary.setAmount(BigDecimal.valueOf(300));
            else
                employeeSalary.setAmount(BigDecimal.valueOf(700));

            employeeSalaryList.add(employeeSalary);

        }


        InventoryFile inventory = new InventoryFile();
        inventory.createInventoryFile(employeeSalaryList);


        //////////////////////// -1-  ////////////////////////////


        //////////////////////// -2-  ////////////////////////////

        PaymentFile paymentFile = new PaymentFile();
        PaymentFileDto dto = new PaymentFileDto();
        dto.setAmount(BigDecimal.valueOf(500000));
        dto.setDepositNo("1.10.100.1");
        dto.setType(Type.debtor);
        paymentFile.addToPaymentFile(dto);


        for (EmployeeSalary x : employeeSalaryList) {
            PaymentFileDto param = new PaymentFileDto();
            param.setAmount(x.getAmount());
            param.setDepositNo(x.getDepositNo());
            param.setType(Type.creditor);
            paymentFile.addToPaymentFile(param);
        }

        //////////////////////// -2-  ////////////////////////////


        //////////////////////// -3-  ////////////////////////////
        String companyDepositNo = "1.10.100.1";
        BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);
        PaymentServiceImpl paymentService = new PaymentServiceImpl();

        BigDecimal sum = paymentService.getAmountSum(employeeSalaryList);
        logger.debug("*****************The sum of the creditors' amounts is:" + sum);
        paymentService.checkInventory(employeeSalaryList, companyBalance, sum);


        //////////////////////// -3-  ////////////////////////////

        //////////////////////// -4-  ////////////////////////////

        List<EmployeeSalary> paymentList = paymentFile.readPaymentFile();

        //////////////////////// -4-  ////////////////////////////


        //////////////////////// -5-  ////////////////////////////
        //////// Do Payment //////

//        PaymentThread paymentThread = new PaymentThread(companyDepositNo, employeeSalaryList);
//        Thread t = new Thread(paymentThread);
//        t.start();
        //////////////////////// -5-  ////////////////////////////
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new RunnableDemo(companyDepositNo,employeeSalaryList));
        executorService.shutdown();
        while (!executorService.isTerminated()){

        }
        logger.debug("*****************Execution completed" );
    }
}

