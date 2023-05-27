import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class RemoveFilm extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        removeFilmScene();
    }

    public static Stage removeFilmScene(){
        Stage removeFilmStage = new Stage();
        ComboBox<String> filmSelecting = new ComboBox<>();
        filmSelecting.setPrefWidth(400);
        ArrayList<String[]> films = Films.films;
        ArrayList<String[]> backup = Films.backup;
        for (String[] film : films) {
            filmSelecting.getItems().addAll(film[1]);
        }
        if (filmSelecting.getItems().size() > 0) filmSelecting.setValue(filmSelecting.getItems().get(0));


        Button backButton = new Button("< BACK");
        Button okButton = new Button("OK");
        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(20);
        buttons.add(backButton,0,0);
        buttons.add(okButton,1,0);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(filmSelecting);
        borderPane.setBottom(buttons);
        Message.topMessage("Select the film that you desire to remove and then click OK.", borderPane);

        backButton.setOnAction(event -> {
            removeFilmStage.close();
            WelcomeAdmin.welcomeScene().show();
        });

        okButton.setOnAction(event -> {
            String filmName = filmSelecting.getValue();
            filmSelecting.getItems().remove(filmName);
            if (filmSelecting.getItems().size() > 0) filmSelecting.setValue(filmSelecting.getItems().get(0));
            Films.removeFilm(filmName);
        });

        Scene scene = new Scene(borderPane, 500,220);
        removeFilmStage.setTitle(Properties.getTitle());
        removeFilmStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        removeFilmStage.setScene(scene);
        removeFilmStage.setResizable(false);
        removeFilmStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return removeFilmStage;
    }
}