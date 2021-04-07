package salary.payment.io;

import salary.payment.model.dto.PaymentFileDto;
import salary.payment.model.enums.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PaymentFile {
    public void addToPaymentFile(PaymentFileDto param) {
        // initialize Path object
        Path path = Paths.get("paymentFile.txt");
        //create file

        try {

            if (!Files.exists(path))
                Files.createFile(path);
            String text =
                    param.toString();
            byte[] bs = text.getBytes();
            Files.write(path, (text + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PaymentFileDto> readPaymentFile() {
        List<PaymentFileDto> paymentFileDtoList = new ArrayList<>();
        Path file = Paths.get("paymentFile.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(Type.creditor.name())) {
                    String li = line.replace(Type.creditor.name() + "\t", "");
                    String deposit = li.substring(0, li.indexOf("\t"));
                    String amount = li.substring(li.indexOf("\t") + 1);
                    amount = amount.replace("\t", "");
                    PaymentFileDto paymentFileDto = new PaymentFileDto();
                    paymentFileDto.setDepositNo(deposit);
                    paymentFileDto.setAmount(new BigDecimal(amount));
                    paymentFileDtoList.add(paymentFileDto);

                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return paymentFileDtoList;
    }
}
