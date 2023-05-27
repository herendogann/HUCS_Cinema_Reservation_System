import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class EditUser extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        editUserScene();
    }
    public static Stage editUserScene(){
        Stage editUserStage = new Stage();
        TableView<Users> tableView = new TableView<Users>();
        ArrayList<String[]> users = Users.users;
        ArrayList<String[]> backup = Films.backup;
        String currentUser = Users.currentUser;

        TableColumn<Users, String> column1 = new TableColumn<>("Username");
        TableColumn<Users, String> column2 = new TableColumn<>("Club Member");
        TableColumn<Users, String> column3 = new TableColumn<>("Admin");

        column1.setCellValueFactory(new PropertyValueFactory<>("username"));
        column2.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        column3.setCellValueFactory(new PropertyValueFactory<>("admin"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        for (String[] user : users){
            if (!user[1].equals(currentUser)) {
                String username = user[1];
                String clubMember = user[3];
                String admin = user[4];
                tableView.getItems().add(new Users(username, clubMember, admin));
            }
        }


        Button backButton = new Button("< BACK");
        Button clubMemberButton = new Button("Promote/Demote Club Member");
        Button adminButton = new Button("Promote/Demote Admin");

        tableView.setOnMouseClicked(event -> {
            String username = tableView.getSelectionModel().selectedItemProperty().get().getUsername();
            String clubMember = tableView.getSelectionModel().selectedItemProperty().get().getClubMember();
            String admin = tableView.getSelectionModel().selectedItemProperty().get().getAdmin();
            clubMemberButton.setOnAction(event1 -> {
                if (clubMember.equals("true")) {
                    tableView.getSelectionModel().selectedItemProperty().get().setClubMember("false");
                    tableView.refresh();
                    for (String[] user : users){
                        if (user[1].equals(username)){
                            user[3] = "false";
                        }
                    }
                    for (String[] user : backup){
                        if (user[0].equals("user")) {
                            if (user[1].equals(username)) {
                                user[3] = "false";
                            }
                        }
                    }
                } else if (clubMember.equals("false")){
                    tableView.getSelectionModel().selectedItemProperty().get().setClubMember("true");
                    tableView.refresh();
                    for (String[] user : users){
                        if (user[1].equals(username)){
                            user[3] = "true";
                        }
                    }
                    for (String[] user : backup){
                        if (user[0].equals("user")) {
                            if (user[1].equals(username)) {
                                user[3] = "true";
                            }
                        }
                    }
                }
            });
            adminButton.setOnAction(event1 -> {
                if (admin.equals("true")) {
                    tableView.getSelectionModel().selectedItemProperty().get().setAdmin("false");
                    tableView.refresh();
                    for (String[] user : users){
                        if (user[1].equals(username)){
                            user[4] = "false";
                        }
                    }
                    for (String[] user : backup){
                        if (user[0].equals("user")) {
                            if (user[1].equals(username)) {
                                user[4] = "false";
                            }
                        }
                    }
                } else if (admin.equals("false")){
                    tableView.getSelectionModel().selectedItemProperty().get().setAdmin("true");
                    tableView.refresh();
                    for (String[] user : users){
                        if (user[1].equals(username)){
                            user[4] = "true";
                        }
                    }
                    for (String[] user : backup){
                        if (user[0].equals("user")) {
                            if (user[1].equals(username)) {
                                user[4] = "true";
                            }
                        }
                    }
                }
            });
        });

        backButton.setOnAction(event -> {
            editUserStage.close();

            WelcomeAdmin.welcomeScene().show();
        });

        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(backButton, clubMemberButton, adminButton);

        VBox vBox = new VBox(15);
        vBox.getChildren().addAll(tableView, buttons);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(vBox);


        Scene scene = new Scene(borderPane, 555,500);
        editUserStage.setTitle(Properties.getTitle());
        editUserStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        editUserStage.setScene(scene);
        editUserStage.setResizable(false);
        editUserStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return editUserStage;
    }
}
