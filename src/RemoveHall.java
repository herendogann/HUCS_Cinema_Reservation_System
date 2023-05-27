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


public class RemoveHall extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        removeHallScene();
    }

    public static Stage removeHallScene(){
        Stage removeHallStage = new Stage();
        ComboBox<String> hallSelecting = new ComboBox<>();
        hallSelecting.setPrefWidth(200);
        String filmName = Films.currentFilm;
        ArrayList<String[]> backup = Films.backup;
        ArrayList<String[]> halls = Halls.halls;
        for (String[] hall : halls) {
            if (hall[1].equals(filmName)) hallSelecting.getItems().addAll(hall[2]);
        }
        if (hallSelecting.getItems().size() > 0) hallSelecting.setValue(hallSelecting.getItems().get(0));

        Button backButton = new Button("< BACK");
        Button okButton = new Button("OK");
        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(20);
        buttons.add(backButton,0,0);
        buttons.add(okButton,1,0);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(hallSelecting);
        borderPane.setBottom(buttons);
        Message.topMessage("Select the film that you desire to remove from "+ filmName +" and then click OK.", borderPane);

        backButton.setOnAction(event -> {
            removeHallStage.close();
            FilmWindowAdmin.filmWindowAdminScene().show();
        });

        okButton.setOnAction(event -> {
            String hallName = hallSelecting.getValue();
            hallSelecting.getItems().remove(hallName);
            if (hallSelecting.getItems().size() > 0) hallSelecting.setValue(hallSelecting.getItems().get(0));
            Halls.removeHall(hallName);
        });

        Scene scene = new Scene(borderPane, 700,220);
        removeHallStage.setTitle(Properties.getTitle());
        removeHallStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        removeHallStage.setScene(scene);
        removeHallStage.setResizable(false);
        removeHallStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return removeHallStage;
    }
}
