import java.util.ArrayList;

public class Films {
    static ArrayList<String[]> films = Reader.backupFilmReader();
    static ArrayList<String[]> halls = Reader.backupHallReader();
    static ArrayList<String[]> backup = Reader.backupReader();
    static String currentFilm = "";

    public static void addFilm(String filmName, String trailerPath, int duration) {
        films.add(new String[]{"film", filmName, trailerPath, String.valueOf(duration)});
        backup.add(new String[]{"film", filmName, trailerPath, String.valueOf(duration)});
    }
    public static void removeFilm(String filmName){
        films.removeIf(film -> film[1].equals(filmName));
        backup.removeIf(line -> line[0].equals("film") && line[1].equals(filmName));

        for (String[] hall : halls) {
            if (hall[1].equals(filmName)){
                Halls.removeHall(hall[2]);
            }
        }
        for (String[] hall : Halls.halls) {
            if (hall[1].equals(filmName)){
                Halls.removeHall(hall[2]);
            }
        }
    }
    public static ArrayList<String> getFilmNameList(){
        ArrayList<String> filmNameList = new ArrayList<>();
        for (String[] film : films) {
            filmNameList.add(film[1]);
        } return filmNameList;
    }

    public static ArrayList<String> getTrailerPathList() {
        ArrayList<String> trailerPathList = new ArrayList<>();
        for (String[] film : films) {
            trailerPathList.add(film[2]);
        } return trailerPathList;
    }

    public static String getTrailerPath() {
        String trailerPath = "";
        for (String[] film : films) {
            if (film[1].equals(currentFilm)) {
                trailerPath = film[2];
            }
        } return trailerPath;
    }

    public static int getDuration() {
        int duration = 0;
        for (String[] film : films) {
            if (film[1].equals(currentFilm)) {
                duration = Integer.parseInt(film[3]);
            }
        } return duration;
    }
}
