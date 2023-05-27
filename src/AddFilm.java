import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;


public class AddFilm extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        addFilmScene();

    }

    public static Stage addFilmScene(){
        ArrayList<String[]> backup = Films.backup;
        ArrayList<String[]> films = Films.films;
        Stage addFilmStage = new Stage();
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        TextField nameField = new TextField();
        TextField trailerField = new TextField();
        TextField durationField = new TextField();

        Text nameInput = new Text();
        Text trailerInput = new Text();
        Text durationInput = new Text();

        Button backButton = new Button("< BACK");
        Button okButton = new Button("OK");

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(new Label("Trailer (path):"), 0,1);
        gridPane.add(trailerField, 1,1);

        gridPane.add(new Label("Duration (m):"), 0,2);
        gridPane.add(durationField, 1,2);

        gridPane.add(backButton, 0,3);
        gridPane.add(okButton,1,3);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);
        Message.topMessage("Please give name, relative path of the trailer and duration of the film.", borderPane);

        backButton.setOnAction(event -> {
            addFilmStage.close();
            WelcomeAdmin.welcomeScene().show();
        });

        okButton.setOnAction(event -> {
            nameInput.setText(nameField.getText());
            trailerInput.setText(trailerField.getText());
            durationInput.setText(durationField.getText());
            String filmName = nameInput.getText();
            String trailerPath = trailerInput.getText();
            String durationString = durationInput.getText();

            ArrayList<String> filmNameList = Films.getFilmNameList();
            ArrayList<String> trailerPathList = Films.getTrailerPathList();


            if (filmName.equals("")){
                Error.errorSound();
                Message.bottomMessage("ERROR: Film name can not be empty!", borderPane);
            } else if (trailerPath.equals("")){
                Error.errorSound();
                Message.bottomMessage("ERROR: Trailer path can not be empty!", borderPane);
            } else if (durationString.equals("")){
                Error.errorSound();
                Message.bottomMessage("ERROR: Duration can not be empty!", borderPane);
            } else {
                try {
                    int duration = Integer.parseInt(durationString);
                    if (duration <= 0){
                        Error.errorSound();
                        Message.bottomMessage("ERROR: Duration has to be a positive integer!", borderPane);
                    } else {
                        if (filmNameList.contains(filmName)){
                            Error.errorSound();
                            Message.bottomMessage("ERROR: '"+ filmName + "' film already exists!", borderPane);
                        } else if (trailerPathList.contains(trailerPath)){
                            Error.errorSound();
                            Message.bottomMessage("ERROR: This trailer already exists!", borderPane);
                        } else {
                            try {
                                new FileReader("assets/trailers/" + trailerPath);
                                Films.addFilm(filmName, trailerPath, duration);
                                Message.bottomMessage("SUCCESS: Film added successfully!", borderPane);
                            } catch (FileNotFoundException e){
                                Error.errorSound();
                                Message.bottomMessage("ERROR: There is no such a trailer!", borderPane);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    Error.errorSound();
                    Message.bottomMessage("ERROR: Duration has to be a positive integer!", borderPane);
                }
            }
        });
        Scene scene = new Scene(borderPane, 600,300);
        addFilmStage.setTitle(Properties.getTitle());
        addFilmStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        addFilmStage.setScene(scene);
        addFilmStage.setResizable(false);
        addFilmStage.setOnCloseRequest(event -> {
        Writer.deleteBackup();
        Writer.writeToBackup(backup);
        });
        return addFilmStage;
    }
}