import java.util.ArrayList;

public class Seats {
    static ArrayList<String[]> seats = Reader.backupSeatReader();
    static ArrayList<String[]> backup = Films.backup;
    static String[] currentSeat = new String[2];

    public static void addSeat(String filmName, String hallName, int row, int column, String ownerName, int boughtPrice) {
        seats.add(new String[]{"seat", filmName, hallName, String.valueOf(row), String.valueOf(column), ownerName, String.valueOf(boughtPrice)});
        backup.add(new String[]{"seat", filmName, hallName, String.valueOf(row), String.valueOf(column), ownerName, String.valueOf(boughtPrice)});
    }
    public static void removeSeats(String hallName){
        seats.removeIf(seat -> seat[2].equals(hallName));
        backup.removeIf(line -> line[0].equals("seat") && line[2].equals(hallName));
    }
    public static void buySeat(String hallName, int row, int column, String ownerName, int boughtPrice){
        for (String[] seat : seats){
            if (seat[2].equals(hallName) && Integer.parseInt(seat[3]) == row && Integer.parseInt(seat[4]) == column){
                seat[5] = ownerName;
                seat[6] = String.valueOf(boughtPrice);
            }
        }
        for (String[] seat : backup){
            if (seat[0].equals("seat")) {
                if (seat[2].equals(hallName) && Integer.parseInt(seat[3]) == row && Integer.parseInt(seat[4]) == column) {
                    seat[5] = ownerName;
                    seat[6] = String.valueOf(boughtPrice);
                }
            }
        }
    }
    public static void refundSeat(String hallName, int row, int column) {
        for (String[] seat : seats){
            if (seat[2].equals(hallName) && Integer.parseInt(seat[3]) == row && Integer.parseInt(seat[4]) == column){
                seat[5] = "null";
                seat[6] = "0";
            }
        }
        for (String[] seat : backup){
            if (seat[0].equals("seat")) {
                if (seat[2].equals(hallName) && Integer.parseInt(seat[3]) == row && Integer.parseInt(seat[4]) == column) {
                    seat[5] = "null";
                    seat[6] = "0";
                }
            }
        }
    }

    public static int getRow(){
        int row = 0;
        for (String[] seat : seats){
            if (seat[3].equals(currentSeat[0]) && seat[4].equals(currentSeat[1])){
                row = Integer.parseInt(seat[3]);
            }
        } return row;
    }
    public static int getColumn(){
        int col = 0;
        for (String[] seat : seats){
            if (seat[3].equals(currentSeat[0]) && seat[4].equals(currentSeat[1])){
                col = Integer.parseInt(seat[4]);
            }
        } return col;
    }
    public static int getBoughtPrice(){
        int price = 0;
        for (String[] seat : seats){
            if (seat[3].equals(currentSeat[0]) && seat[4].equals(currentSeat[1])){
                price = Integer.parseInt(seat[6]);
            }
        } return price;
    }
    public static String getOwner(){
        String owner = "";
        for (String[] seat : seats){
            if (seat[3].equals(currentSeat[0]) && seat[4].equals(currentSeat[1])){
                owner = seat[5];
            }
        } return owner;
    }
}
