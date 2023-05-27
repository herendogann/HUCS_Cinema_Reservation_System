import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static ArrayList<String[]> backupReader() {
        ArrayList<String[]> backup_array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/backup.dat"));
            while (scanner.hasNextLine()) {
                    String[] text_array = scanner.nextLine().split("\t");
                    backup_array.add(text_array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backup_array;
    }

    public static ArrayList<String[]> backupFilmReader() {
        ArrayList<String[]> film_array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/backup.dat"));
            while (scanner.hasNextLine()) {
                String[] text_array = scanner.nextLine().split("\t");
                if (text_array[0].equals("film")) {
                    film_array.add(text_array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return film_array;
    }

    public static ArrayList<String[]> backupUserReader() {
        ArrayList<String[]> user_array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/backup.dat"));
            while (scanner.hasNextLine()) {
                String[] text_array = scanner.nextLine().split("\t");
                if (text_array[0].equals("user")) {
                    user_array.add(text_array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user_array;
    }

    public static ArrayList<String[]> backupHallReader() {
        ArrayList<String[]> hall_array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/backup.dat"));
            while (scanner.hasNextLine()) {
                String[] text_array = scanner.nextLine().split("\t");
                if (text_array[0].equals("hall")) {
                    hall_array.add(text_array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hall_array;
    }

    public static ArrayList<String[]> backupSeatReader() {
        ArrayList<String[]> seat_array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/backup.dat"));
            while (scanner.hasNextLine()) {
                String[] text_array = scanner.nextLine().split("\t");
                if (text_array[0].equals("seat")) {
                    seat_array.add(text_array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seat_array;
    }
    public static ArrayList<String> propertiesReader(){
        ArrayList<String> properties = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("assets/data/properties.dat"));
            while (scanner.hasNextLine()){
                properties.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void print_array(ArrayList<String[]> array) {
        for (String[] line : array) {
            for (String cell : line) {
                if (cell != null) {
                    System.out.print(cell + "\t\t");
                }
            }
            System.out.println();
        }
    }
}
