import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class FilmWindowUser extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        filmWindowUserScene();
    }
    public static Stage filmWindowUserScene() {
        Stage filmWindowUserStage = new Stage();

        ArrayList<String[]> halls = Halls.halls;
        ArrayList<String[]> films = Films.films;
        ArrayList<String[]> backup = Films.backup;
        String filmName = Films.currentFilm;

        int duration = 0;
        for (String[] film : films) {
            if (film[1].equals(filmName)) {
                duration = Integer.parseInt(film[3]);
            }
        }

        BorderPane borderPane = new BorderPane();

        String pathName = "";
        for (String[] film : films) {
            if (filmName.equals(film[1])){
                pathName = film[2];
            }
        }

        File file = new File("assets/trailers/" + pathName);
        String mediaURL = file.toURI().toString();
        Media media = new Media(mediaURL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(540);
        mediaView.setFitWidth(960);

        Text playPause = new Text(">");
        playPause.setFont(Font.font("Arial",15));
        Button playButton = new Button("", playPause);
        Button back5sButton = new Button("<<");
        Button forward5sButton = new Button(">>");
        Button rewindButton = new Button("|<<");

        Slider volumeSlider = new Slider();
        volumeSlider.setOrientation(Orientation.VERTICAL);
        volumeSlider.setValue(50);
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        Button backButton = new Button("< BACK");
        Button okButton = new Button("OK");
        ComboBox<String> hallSelectBox = new ComboBox<>();
        hallSelectBox.setPrefWidth(200);
        for (String[] hall : halls) {
            if (hall[1].equals(filmName)) {
                hallSelectBox.getItems().add(hall[2]);
            }
        }
        hallSelectBox.setValue(hallSelectBox.getItems().get(0));

        VBox mediaButtons = new VBox(20);
        mediaButtons.getChildren().addAll(playButton, back5sButton, forward5sButton, rewindButton, volumeSlider);

        HBox hallSelect = new HBox(31);
        hallSelect.getChildren().addAll(hallSelectBox, okButton);

        HBox buttons = new HBox(300);
        buttons.getChildren().addAll(backButton, hallSelect);

        HBox mediaHBox = new HBox(50);
        mediaHBox.getChildren().addAll(mediaView, mediaButtons);

        borderPane.setPadding(new Insets(30));
        borderPane.setCenter(mediaHBox);
        borderPane.setBottom(buttons);
        Message.topMessage(filmName + " ("+ duration +" minutes)", borderPane);

        backButton.setOnAction(event -> {
            mediaPlayer.pause();
            filmWindowUserStage.close();
            WelcomeUser.welcomeScene().show();
        });

        okButton.setOnAction(event -> {
            mediaPlayer.pause();
            filmWindowUserStage.close();
            Halls.currentHall = hallSelectBox.getValue();
            HallWindowUser.hallWindowUserScene().show();
        });

        playButton.setOnAction(event -> {
            if (playPause.getText().equals(">")) {
                mediaPlayer.play();
                playPause.setText("I I");
            } else if (playPause.getText().equals("I >")) {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
                playPause.setText("I I");
            } else {
                mediaPlayer.pause();
                playPause.setText(">");
            }
        });

        back5sButton.setOnAction(event -> mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() - 5)));
        forward5sButton.setOnAction(event -> {
            if (mediaPlayer.getCurrentTime().toSeconds() + 5 < mediaPlayer.getTotalDuration().toSeconds()) {
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() + 5));
            }
        });
        rewindButton.setOnAction(event -> mediaPlayer.seek(Duration.ZERO));

        mediaPlayer.setOnEndOfMedia(() -> {
                    playPause.setText("I >");
                    mediaPlayer.pause();
                });

        Scene scene = new Scene(borderPane, 1120,720);
        filmWindowUserStage.setTitle(Properties.getTitle());
        filmWindowUserStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        filmWindowUserStage.setScene(scene);
        filmWindowUserStage.setResizable(false);
        filmWindowUserStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return filmWindowUserStage;
    }
}
