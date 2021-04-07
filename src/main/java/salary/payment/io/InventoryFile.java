package salary.payment.io;

import org.apache.log4j.Logger;
import salary.payment.model.dto.InventoryFileDto;
import salary.payment.model.dto.PaymentFileDto;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InventoryFile {
    private final Logger logger = Logger.getLogger(this.getClass());

    public synchronized void updateBalance(InventoryFileDto param) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader("inventoryFile.txt"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();


            boolean found = false;
            String newInput = "";
            for (String li : inputStr.split("\n")) {
                if (li.contains(param.getDepositNo())) {
                    li = param.toString(); // replace the line here
                    found = true;
                }
                newInput = newInput.concat(li + "\n");
            }
            if (!found) {
                newInput = newInput.concat(param.toString() + "\n");
            }

            // display the new file for debugging

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("inventoryFile.txt");
            FileChannel fileChannel = fileOut.getChannel();
            FileLock fileLock = fileChannel.lock();
            fileOut.write(newInput.getBytes());
            fileOut.close();

        } catch (Exception e) {
            logger.debug("******************Problem reading file. ");
        }

    }

    public BigDecimal getDepositBalance(String depositNo) {
        BigDecimal balance = BigDecimal.ZERO;
        Path file = Paths.get("inventoryFile.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(depositNo)) {
                    String amount = line.replace(depositNo, "");
                    amount = amount.replace("\t", "");
                    if (amount.equals("0"))
                        balance = BigDecimal.ZERO;
                    else
                        balance = new BigDecimal(amount);

                    logger.debug("******************Company Balance is : " + balance);
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return balance;
    }

    public void createInventoryFile(List<PaymentFileDto> paymentFileDtos) {
        String str = "1.10.100.1\t500000\n";
        String x = "";
        for (PaymentFileDto paymentFileDto : paymentFileDtos) {
            x = paymentFileDto.getDepositNo() + "\t0\n";

        }
        str = str + x;
        //initialize Path object
        Path path = Paths.get("inventoryFile.txt");
        //create file
        try {

            byte[] bs = str.getBytes();
            Path writtenFilePath = Files.write(path, bs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
