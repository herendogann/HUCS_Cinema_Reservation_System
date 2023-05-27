import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class HallWindowUser extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        hallWindowUserScene();
    }

    public static Stage hallWindowUserScene(){
        Stage hallWindowUserStage = new Stage();
        String username = Users.currentUser;
        String filmName = Films.currentFilm;
        String hallName = Halls.currentHall;
        ArrayList<String[]> users = Users.users;
        ArrayList<String[]> backup = Films.backup;
        ArrayList<String[]> films = Films.films;
        ArrayList<String[]> halls = Films.films;
        ArrayList<String[]> seats = Seats.seats;
        ArrayList<String[]> seatsList = Halls.getSeatsList();


        GridPane seatsPane = new GridPane();
        seatsPane.setHgap(5); seatsPane.setVgap(5);
        seatsPane.setAlignment(Pos.TOP_CENTER);
        Button backButton = new Button("< BACK");

        int duration = Films.getDuration();
        int row = Halls.getRow();
        int column = Halls.getColumn();


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(seatsPane);
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


            seatButton.setOnMouseClicked(event -> {
                for (String[] seat1 : seatsList) {
                    if (seat1[3].equals(seatButton.getText().split("-")[0]) && seat1[4].equals(seatButton.getText().split("-")[1])) {
                        if (seat1[5].equals("null")) {

                            int price = 0;
                            for (String[] user : users) {
                                if (username.equals(user[1])) {
                                    if (user[3].equals("false")) price = Halls.getPrice();
                                    if (user[3].equals("true")) price = Math.toIntExact(Math.round(Halls.getPrice() * 0.9));
                                }
                            }
                            Seats.buySeat(hallName, Integer.parseInt(seat1[3]), Integer.parseInt(seat1[4]), username, price);
                            VBox bought = new VBox(5);
                            new TextPane("Seat at " + (Integer.parseInt(seat1[4]) + 1) + " - " + (Integer.parseInt(seat1[3]) + 1) + " is bought for " + price + " TL successfully.", bought);
                            seatButton.setGraphic(Images.getReservedSeat());
                            VBox paneBought = new VBox(10);
                            paneBought.getChildren().addAll(seatsPane, bought);
                            paneBought.setAlignment(Pos.TOP_CENTER);
                            borderPane.setCenter(paneBought);
                        } else {
                            Seats.refundSeat(hallName, Integer.parseInt(seat1[3]), Integer.parseInt(seat1[4]));
                            VBox refund = new VBox(5);
                            new TextPane("Seat at " + (Integer.parseInt(seat1[4]) + 1) + " - " + (Integer.parseInt(seat1[3]) + 1) + " refunded successfully.", refund);
                            seatButton.setGraphic(Images.getEmptySeat());
                            VBox paneRefund = new VBox(10);
                            paneRefund.getChildren().addAll(seatsPane, refund);
                            paneRefund.setAlignment(Pos.TOP_CENTER);
                            borderPane.setCenter(paneRefund);
                        }
                    }
                }
            });

            seatsPane.add(seatButton, Seats.getColumn(), Seats.getRow());
            for (String[] seat1 : seatsList) {
                if (seat1[3].equals(seatButton.getText().split("-")[0]) && seat1[4].equals(seatButton.getText().split("-")[1])) {
                    if (!seat1[5].equals("null")) {
                        if (!seat1[5].equals(username)) {
                            reservedSeat.setOpacity(0.5);
                            seatButton.setGraphic(reservedSeat);
                            seatButton.setDisable(true);
                        } else {
                            seatButton.setGraphic(reservedSeat);
                        }
                    }
                }
            }
        }



        backButton.setOnAction(event -> {
            hallWindowUserStage.close();
            FilmWindowUser.filmWindowUserScene().show();
        });

        int minWidth = 500;
        int minHeight = 500;
        int width = column * 80;
        int height = row * 90;
        if (column * 80 < minWidth) width = minWidth;
        if (row * 90 < minHeight) height = minHeight;

        Scene scene = new Scene(borderPane, width, height);
        hallWindowUserStage.setTitle(Properties.getTitle());
        hallWindowUserStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        hallWindowUserStage.setScene(scene);
        hallWindowUserStage.setResizable(false);
        hallWindowUserStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return hallWindowUserStage;
    }
}
