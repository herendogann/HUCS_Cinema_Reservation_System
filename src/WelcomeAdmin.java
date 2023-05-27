import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class WelcomeAdmin extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        welcomeScene();
    }

    public static Stage welcomeScene(){
        String username = Users.currentUser;
        ArrayList<String[]> users = Users.users;
        ArrayList<String[]> backup = Films.backup;
        Stage welcomeStage = new Stage();
        ComboBox<String> filmSelectingBox = new ComboBox<>();
        filmSelectingBox.setPrefWidth(400);
        ArrayList<String[]> films = Films.films;
        for (String[] film : films) {
            filmSelectingBox.getItems().addAll(film[1]);
        }
        if (filmSelectingBox.getItems().size() > 0) filmSelectingBox.setValue(filmSelectingBox.getItems().get(0));

        Button logoutButton = new Button("LOG OUT");
        Button okButton = new Button("OK");

        Button addFilmButton = new Button("Add Film");
        Button removeFilmButton = new Button("Remove Film");
        Button editUsersButton = new Button("Edit Users");

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(addFilmButton,removeFilmButton,editUsersButton);

        VBox vBox = new VBox(10);
        HBox filmSelect = new HBox(10);
        filmSelect.getChildren().addAll(filmSelectingBox, okButton);
        vBox.getChildren().addAll(filmSelect,buttons, logoutButton);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        String upText = "";
        for (String[] user : users) {
            if (username.equals(user[1])) {
                if (user[3].equals("false")) {
                    upText = "Welcome " + username + "! (Admin)\nSelect a film then click OK to continue.";
                } else if (user[3].equals("true")) {
                    upText = "Welcome " + username + "! (Admin - Club Member)\nSelect a film then click OK to continue.";
                }
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(vBox);
        Message.topMessage(upText, borderPane);

        logoutButton.setOnAction(event -> {
            Users.currentUser = "";
            welcomeStage.close();
            Login.loginScene().show();
        });

        addFilmButton.setOnAction(event -> {
            welcomeStage.close();
            AddFilm.addFilmScene().show();
        });

        removeFilmButton.setOnAction(event -> {
            if (filmSelectingBox.getItems().size() > 0) {
                welcomeStage.close();
                RemoveFilm.removeFilmScene().show();
            }
        });

        editUsersButton.setOnAction(event -> {
            welcomeStage.close();
            EditUser.editUserScene().show();
        });

        okButton.setOnAction(event -> {
            if (filmSelectingBox.getItems().size() > 0) {
                Films.currentFilm = filmSelectingBox.getValue();
                welcomeStage.close();
                FilmWindowAdmin.filmWindowAdminScene().show();
            }
        });

        Scene scene = new Scene(borderPane, 490,220);
        welcomeStage.setTitle(Properties.getTitle());
        welcomeStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        welcomeStage.setScene(scene);
        welcomeStage.setResizable(false);
        welcomeStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return welcomeStage;
    }
}