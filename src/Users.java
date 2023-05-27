import java.util.ArrayList;

public class Users {
    static ArrayList<String[]> users = Reader.backupUserReader();
    static ArrayList<String[]> backup = Films.backup;
    static String currentUser = "";
    private String username = null;
    private String clubMember = null;
    private String admin = null;


    public Users(String username, String clubMember, String admin) {
        this.username = username;
        this.clubMember = clubMember;
        this.admin = admin;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getClubMember() {return clubMember;}
    public void setClubMember(String clubMember) {this.clubMember = clubMember;}

    public String getAdmin() {return admin;}
    public void setAdmin(String admin) {this.admin = admin;}

    public static void addUser(String username, String hashedPassword, Boolean clubMember, Boolean admin){
        users.add(new String[]{"user", username, hashedPassword, String.valueOf(clubMember), String.valueOf(admin)});
        backup.add(new String[]{"user", username, hashedPassword, String.valueOf(clubMember), String.valueOf(admin)});
    }

    public static ArrayList<String> getUsernameList(){
        ArrayList<String> usernameList = new ArrayList<>();
        for (String[] user : users) {
            usernameList.add(user[1]);
        } return usernameList;
    }
}
