import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class AddHall extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        addHallScene();
    }

    public static Stage addHallScene(){
        ArrayList<String[]> films = Films.films;
        ArrayList<String[]> halls = Halls.halls;
        ArrayList<String[]> backup = Films.halls;
        Stage addHallStage = new Stage();
        GridPane rowColumnBoxes = new GridPane();
        GridPane namePriceFields = new GridPane();
        String filmName = Films.currentFilm;

        int duration = 0;
        for (String[] film : films) {
            if (film[1].equals(filmName)) {
                duration = Integer.parseInt(film[3]);
            }
        }

        rowColumnBoxes.setAlignment(Pos.CENTER);
        rowColumnBoxes.setHgap(10);
        rowColumnBoxes.setVgap(10);

        namePriceFields.setAlignment(Pos.CENTER);
        namePriceFields.setHgap(10);
        namePriceFields.setVgap(10);

        ComboBox<Integer> rowBox = new ComboBox<>();
        ComboBox<Integer> columnBox = new ComboBox<>();
        for (int i = 3; i <= 10; i++) {
            rowBox.getItems().add(i);
            columnBox.getItems().add(i);
        }
        rowBox.setValue(3);
        columnBox.setValue(3);

        TextField nameField = new TextField();
        TextField priceField = new TextField();

        Text hallNameInput = new Text();
        Text priceInput = new Text();

        Button backButton = new Button("< BACK");
        Button okButton = new Button("OK");

        HBox buttons = new HBox(128);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(backButton, okButton);

        rowColumnBoxes.add(new Label("Row:"), 0, 0);
        rowColumnBoxes.add(rowBox, 1, 0);

        rowColumnBoxes.add(new Label("Column:"), 0,1);
        rowColumnBoxes.add(columnBox, 1,1);

        namePriceFields.add(new Label("Name:"), 0,2);
        namePriceFields.add(nameField, 1,2);

        namePriceFields.add(new Label("Price:"), 0,3);
        namePriceFields.add(priceField,1,3);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(rowColumnBoxes, namePriceFields, buttons);


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(vBox);
        Message.topMessage(filmName + " (" + duration + " minutes)", borderPane);

        backButton.setOnAction(event -> {
            addHallStage.close();
            FilmWindowAdmin.filmWindowAdminScene().show();
        });

        okButton.setOnAction(event -> {
            hallNameInput.setText(nameField.getText());
            priceInput.setText(priceField.getText());
            String hallName = hallNameInput.getText();
            String priceString = priceInput.getText();
            int row = rowBox.getValue();
            int column = columnBox.getValue();

            ArrayList<String> hallNameList = Halls.getHallNameList();

            if (hallName.equals("")){
                Error.errorSound();
                Message.bottomMessage("ERROR: Hall name can not be empty!", borderPane);
            } else if (priceString.equals("")) {
                Error.errorSound();
                Message.bottomMessage("ERROR: Price can not be empty!", borderPane);
            } else {
                try {
                    int price = Integer.parseInt(priceString);
                    if (price <= 0) {
                        Error.errorSound();
                        Message.bottomMessage("ERROR: Price has to be a positive integer!", borderPane);
                    } else {
                        if (hallNameList.contains(hallName)){
                            Error.errorSound();
                            Message.bottomMessage("ERROR: '"+ hallName + "' hall already exists!", borderPane);
                        } else {
                            Halls.addHall(filmName,hallName,price,row,column);
                            Message.bottomMessage("SUCCESS: Hall successfully created!", borderPane);
                        }
                    }
                } catch (NumberFormatException e) {
                    Error.errorSound();
                    Message.bottomMessage("ERROR: Price has to be a positive integer!", borderPane);
                }
            }
        });

        Scene scene = new Scene(borderPane, 500,330);
        addHallStage.setTitle(Properties.getTitle());
        addHallStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        addHallStage.setScene(scene);
        addHallStage.setResizable(false);
        addHallStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return addHallStage;
    }
}