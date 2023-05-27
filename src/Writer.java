import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Writer {
    public static void write(String fileName, String output) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, true);
            writer.write(output);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void deleteBackup(){
        File file = new File("assets/data/backup.dat");
        if (file.delete()) System.out.println("backup.dat deleted successfully.");
        else file.deleteOnExit(); System.out.println("backup.dat deleted on exit.");
    }

    public static void writeToBackup(ArrayList<String[]> backup) {

        File backupFile = new File("assets/data/backup.dat");
        if (!backupFile.exists()) {
            try {
                if (backupFile.createNewFile()) System.out.println("new backup.dat created successfully.");
                else System.out.println("Fail, backup.dat not created");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String[] line : backup) {
                for (String cell : line) {
                    if (cell != null) {
                        write("assets/data/backup.dat", cell + "\t");
                    }
                }
                write("assets/data/backup.dat", "\n");
            }
        }
    }
}