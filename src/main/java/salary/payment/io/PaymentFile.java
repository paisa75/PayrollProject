package salary.payment.io;

import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.enums.Type;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PaymentFile {
    public void addToPaymentFile(PaymentFileDto param) {
        // initialize Path object
        Path path = Paths.get("paymentFile.txt");
        //create file

        try {

            if (!Files.exists(path))
                Files.createFile(path);
            String text = param.toString();
            byte[] bs = text.getBytes();
            // Path writtenFilePath = Files.write(path, bs);

            Files.write(path, (text + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            // System.out.println("Inventory is equal to:\n"+ new String(Files.readAllBytes(writtenFilePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
