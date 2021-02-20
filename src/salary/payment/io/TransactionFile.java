import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TransactionFile {


    public void createTransactionFile(String deptorDepositNo, String creditorDepositNo, BigDecimal amount) {
        // initialize Path object
        Path path = Paths.get("transactionFile.txt");
        //create file

        try {

            if (!Files.exists(path))
                Files.createFile(path);
            String text = deptorDepositNo + "\t" + creditorDepositNo + "\t" + amount;
            byte[] bs = text.getBytes();
            // Path writtenFilePath = Files.write(path, bs);

            Files.write(path, (text + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            // System.out.println("Inventory is equal to:\n"+ new String(Files.readAllBytes(writtenFilePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
