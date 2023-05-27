import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class HallWindowAdmin extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        hallWindowAdminScene();
    }

    public static Stage hallWindowAdminScene(){
        Stage hallWindowAdminStage = new Stage();
        String username = Users.currentUser;
        String filmName = Films.currentFilm;
        String hallName = Halls.currentHall;
        ArrayList<String[]> backup = Films.backup;
        ArrayList<String[]> films = Films.films;
        ArrayList<String[]> halls = Films.films;
        ArrayList<String[]> seats = Seats.seats;
        ArrayList<String[]> users = Users.users;
        ArrayList<String[]> seatsList = Halls.getSeatsList();

        ComboBox<String> usersComboBox = new ComboBox<>();
        usersComboBox.setPrefWidth(150);
        for (String[] user : users){
            usersComboBox.getItems().add(user[1]);
        }
        usersComboBox.setValue(username);


        GridPane seatsPane = new GridPane();
        seatsPane.setHgap(5); seatsPane.setVgap(5);
        seatsPane.setAlignment(Pos.TOP_CENTER);

        VBox boxAndPane = new VBox(10);
        boxAndPane.setAlignment(Pos.TOP_CENTER);
        boxAndPane.getChildren().addAll(seatsPane, usersComboBox);

        Button backButton = new Button("< BACK");

        int duration = Films.getDuration();
        int row = Halls.getRow();
        int column = Halls.getColumn();

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(boxAndPane);
        borderPane.setBottom(backButton);

        Message.topMessage(filmName + " ("+ duration +" minutes) - Hall: " + hallName, borderPane);

        for (String[] seat : seatsList){
            ImageView emptySeat = Images.getEmptySeat();
            ImageView reservedSeat = Images.getReservedSeat();

            Seats.currentSeat = new String[]{seat[3], seat[4]};
            Button seatButton = new Button(seat[3] + "-" + seat[4]);
            seatButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            seatButton.setPrefSize(50,50);
            seatButton.setGraphic(emptySeat);




            seatButton.setOnMouseEntered(event -> {
                Text moveText = new Text(" ");
                Text clickText = new Text(" ");
                for (String[] seat1 : seatsList) {
                    if (seat1[3].equals(seatButton.getText().split("-")[0]) && seat1[4].equals(seatButton.getText().split("-")[1])) {
                        if (seat1[5].equals("null")) {
                            moveText.setText("Not bought yet!");
                        } else {
                            String owner = seat1[5];
                            int price = Integer.parseInt(seat1[6]);
                            moveText.setText("Bought by " + owner + " for " + price + " TL.");
                        }
                    }
                }


                seatButton.setOnMouseClicked(event1 -> {
                    String selectedUser = usersComboBox.getValue();

                    for (String[] seat1 : seatsList) {
                        if (seat1[3].equals(seatButton.getText().split("-")[0]) && seat1[4].equals(seatButton.getText().split("-")[1])) {
                            if (seat1[5].equals("null")) {

                                int price = 0;
                                for (String[] user : users) {
                                    if (selectedUser.equals(user[1])) {
                                        if (user[3].equals("false")) price = Halls.getPrice();
                                        if (user[3].equals("true")) price = Math.toIntExact(Math.round(Halls.getPrice() * 0.9));
                                    }
                                }
                                Seats.buySeat(hallName, Integer.parseInt(seat1[3]), Integer.parseInt(seat1[4]), selectedUser, price);
                                seatButton.setGraphic(Images.getReservedSeat());
                                clickText.setText("Seat at " + (Integer.parseInt(seat1[4]) + 1) + " - " + (Integer.parseInt(seat1[3]) + 1) + " is bought for "
                                        + selectedUser + " for "+ price + " TL successfully.");
                            } else {
                                String owner = seat1[5];
                                Seats.refundSeat(hallName, Integer.parseInt(seat1[3]), Integer.parseInt(seat1[4]));
                                seatButton.setGraphic(Images.getEmptySeat());
                                clickText.setText("Seat at " + (Integer.parseInt(seat1[4]) + 1) + " - " + (Integer.parseInt(seat1[3]) + 1) + " refunded from " + owner + " successfully.");
                            }
                        }
                    }
                    VBox click = new VBox(5);
                    new TextPane(clickText.getText(), click);
                    HBox backClick = new HBox(31);
                    backClick.getChildren().addAll(backButton, click);
                    backClick.setAlignment(Pos.TOP_LEFT);
                    borderPane.setBottom(backClick);
                });
                VBox move = new VBox(5);
                new TextPane(moveText.getText(), move);
                VBox paneBoxClickMove = new VBox(10);
                paneBoxClickMove.getChildren().addAll(boxAndPane, move);
                paneBoxClickMove.setAlignment(Pos.TOP_CENTER);
                borderPane.setCenter(paneBoxClickMove);
            });

            seatsPane.add(seatButton, Seats.getColumn(), Seats.getRow());
            for (String[] seat1 : seatsList) {
                if (seat1[3].equals(seatButton.getText().split("-")[0]) && seat1[4].equals(seatButton.getText().split("-")[1])) {
                    if (!seat1[5].equals("null")) {
                        seatButton.setGraphic(reservedSeat);
                    }
                }
            }

        }



        backButton.setOnAction(event -> {
            hallWindowAdminStage.close();
            FilmWindowAdmin.filmWindowAdminScene().show();
        });


        int minWidth = 600;
        int minHeight = 500;
        int width = column * 80;
        int height = row * 95;
        if (column * 80 < minWidth) width = minWidth;
        if (row * 95 < minHeight) height = minHeight;

        Scene scene = new Scene(borderPane, width, height);
        hallWindowAdminStage.setTitle(Properties.getTitle());
        hallWindowAdminStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        hallWindowAdminStage.setScene(scene);
        hallWindowAdminStage.setResizable(false);
        hallWindowAdminStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return hallWindowAdminStage;
    }
}