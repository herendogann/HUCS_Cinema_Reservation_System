import java.util.ArrayList;

public class Halls {
    static ArrayList<String[]> halls = Reader.backupHallReader();
    static ArrayList<String[]> backup = Films.backup;
    static String currentHall = "";

    public static void addHall(String filmName, String hallName, int price, int row, int column) {
        halls.add(new String[]{"hall", filmName, hallName, String.valueOf(price), String.valueOf(row), String.valueOf(column)});
        backup.add(new String[]{"hall", filmName, hallName, String.valueOf(price), String.valueOf(row), String.valueOf(column)});
        for (int r = 0; r < row; r++){
            for (int c = 0; c < column; c++){
                Seats.addSeat(filmName,hallName,r,c, "null", 0);
            }
        }
    }
    public static void removeHall(String hallName){
        halls.removeIf(hall -> hall[2].equals(hallName));
        backup.removeIf(line -> line[0].equals("hall") && line[2].equals(hallName));
        Seats.removeSeats(hallName);
    }

    public static ArrayList<String> getHallNameList(){
        ArrayList<String> hallNameList = new ArrayList<>();
        for (String[] hall : halls) {
            hallNameList.add(hall[2]);
        } return hallNameList;
    }
    public static ArrayList<String[]> getSeatsList(){
        ArrayList<String[]> seatsList = new ArrayList<>();
        for (String[] seat : Seats.seats){
            if (seat[2].equals(currentHall)) {
                seatsList.add(seat);
            }
        } return seatsList;
    }

    public static int getPrice(){
        int price = 0;
        for (String[] hall : halls){
            if (hall[2].equals(currentHall)){
                price = Integer.parseInt(hall[3]);
            }
        } return price;
    }
    public static int getRow(){
        int row = 0;
        for (String[] hall : halls){
            if (hall[2].equals(currentHall)){
                row = Integer.parseInt(hall[4]);
            }
        } return row;
    }

    public static int getColumn(){
        int column = 0;
        for (String[] hall : halls){
            if (hall[2].equals(currentHall)){
                column = Integer.parseInt(hall[5]);
            }
        } return column;
    }
}
