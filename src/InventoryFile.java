import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InventoryFile {

    public static void replaceSelected(String depositNo, BigDecimal amount) {
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

            System.out.println(inputStr); // display the original file for debugging
            String text = depositNo + "\t" + amount;
            boolean found = false;
            String newInput = "";
            for (String li : inputStr.split("/n")) {
                if (li.contains(depositNo)) {
                    li = text; // replace the line here
                    found = true;
                }
                newInput = newInput.concat(li);
            }
            if (!found) {
                newInput = newInput.concat(text + "\n");
            }
            System.out.println(newInput);
            // logic to replace lines in the string (could use regex here to be generic)
            /*if (type.equals("0")) {
                inputStr = inputStr.replace(replaceWith + "1", replaceWith + "0");
            } else if (type.equals("1")) {
                inputStr = inputStr.replace(replaceWith + "0", replaceWith + "1");
            }
*/
            // display the new file for debugging
            System.out.println("----------------------------------\n" + inputStr);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("inventoryFile.txt");
            fileOut.write(newInput.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }


    }

    public void createInventoryFile() {
        //initialize Path object
        Path path = Paths.get("inventoryFile.txt");
        //create file
        try {
            String str = "1.10.100.1\t1000";
            byte[] bs = str.getBytes();
            Path writtenFilePath = Files.write(path, bs);
            //System.out.println("Inventory is equal to:\n"+ new String(Files.readAllBytes(writtenFilePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void updateBalance(String depositNo, BigDecimal amount) {
//        try {
//            // input the (modified) file content to the StringBuffer "input"
//            BufferedReader file = new BufferedReader(new FileReader("inventoryFile.txt"));
//            StringBuffer inputBuffer = new StringBuffer();
//            String line;
//            String text = depositNo + "\t" + amount;
//            boolean found = false;
//            while ((line = file.readLine()) != null) {
//                if (line.contains(depositNo)) {
//                    line = text; // replace the line here
//                    found = true;
//                }
//                inputBuffer.append(line);
//                inputBuffer.append('\n');
//            }
//            file.close();
//
//            // write the new string with the replaced line OVER the same file
//            FileOutputStream fileOut = new FileOutputStream("inventoryFile.txt");
//            if (found)
//                fileOut.write(inputBuffer.toString().getBytes());
//            else fileOut.write(text.getBytes());
//            fileOut.close();
//
//        } catch (Exception e) {
//            System.out.println("Problem reading file.");
//        }
//    }
}
