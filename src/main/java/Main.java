import org.apache.log4j.Logger;
import salary.payment.io.InventoryFile;
import salary.payment.io.PaymentFile;
import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.enums.Type;
import salary.payment.service.PaymentServiceImpl;
import salary.payment.thread.PaymentThread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger logger = Logger.getLogger("Main.class");

    public static void main(String[] args) {
        logger.debug("*********************** main class started");

        List<PaymentFileDto> paymentFileDtoList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            PaymentFileDto paymentFileDto = new PaymentFileDto();
            paymentFileDto.setDepositNo("1.20.100." + i);
            if (i % 2 == 0)
                paymentFileDto.setAmount(BigDecimal.valueOf(300));
            else
                paymentFileDto.setAmount(BigDecimal.valueOf(700));
            paymentFileDtoList.add(paymentFileDto);
        }
        InventoryFile inventory = new InventoryFile();
        inventory.createInventoryFile(paymentFileDtoList);


        PaymentFile paymentFile = new PaymentFile();
        PaymentFileDto dto = new PaymentFileDto();
        dto.setAmount(BigDecimal.valueOf(500000));
        dto.setDepositNo("1.10.100.1");
        dto.setType(Type.debtor);
        paymentFile.addToPaymentFile(dto);


        for (PaymentFileDto fileDto : paymentFileDtoList) {
            PaymentFileDto param = new PaymentFileDto();
            param.setAmount(fileDto.getAmount());
            param.setDepositNo(fileDto.getDepositNo());
            param.setType(Type.creditor);
            paymentFile.addToPaymentFile(param);
        }


        String companyDepositNo = "1.10.100.1";
        BigDecimal companyBalance = inventory.getDepositBalance(companyDepositNo);
        PaymentServiceImpl paymentService = new PaymentServiceImpl();

        BigDecimal sum = paymentService.getAmountSum(paymentFileDtoList);
        logger.debug("*****************The sum of the creditors' amounts is:" + sum);
        paymentService.checkInventory(paymentFileDtoList, companyBalance, sum);

        List<PaymentFileDto> fileDtoList = paymentFile.readPaymentFile();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new PaymentThread(companyDepositNo, paymentFileDtoList));

        executorService.shutdown();
        logger.debug("*****************Execution completed");
    }
}
