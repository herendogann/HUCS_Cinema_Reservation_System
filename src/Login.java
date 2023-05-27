
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Login extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        loginScene();
    }

    public static Stage loginScene() {
        Stage loginStage = new Stage();
        GridPane gridPane = new GridPane();
        ArrayList<String[]> users = Users.users;
        ArrayList<String[]> backup = Films.backup;
        ArrayList<String> usernameList = new ArrayList<>();
        for (String[] user : users) {
            usernameList.add(user[1]);
        }
        if (!usernameList.contains("admin")) {
            Users.addUser("admin", hashPassword("password"), true, true);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("LOGIN");
        Button signUpButton = new Button("SIGN UP");

        Text usernameInput = new Text();
        Text passwordInput = new Text();


        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);

        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);

        gridPane.add(signUpButton, 0, 2);
        gridPane.add(loginButton, 1, 2);


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(30));
        borderPane.setCenter(gridPane);
        Message.topMessage("Welcome to the HUCS Cinema Reservation System!\n" +
                "Please enter your credentials below and click LOGIN.\n" +
                "You can create a new account by clicking SIGN UP button.", borderPane);
        Text freeTimeText = new Text("0");

        loginButton.setOnAction(event -> {
            if (System.currentTimeMillis() < Long.parseLong(freeTimeText.getText()) && !freeTimeText.getText().equals("0") && Error.errorCount == 0){
                Error.errorSound();
                Message.bottomMessage("ERROR: Please wait until the end of " + Properties.getBlockTime() + " seconds to make a new operation.", borderPane);
            } else {
                usernameInput.setText(usernameField.getText());
                passwordInput.setText(passwordField.getText());
                String usernameInputString = usernameInput.getText();
                String hashedPassword = hashPassword(passwordInput.getText());

                for (String[] user : users) {
                    if (usernameInputString.equals(user[1]) && hashedPassword.equals(user[2]) ) {
                        Users.currentUser = usernameInputString;
                        loginStage.close();
                        if (user[4].equals("false")) WelcomeUser.welcomeScene().show();
                        if (user[4].equals("true")) WelcomeAdmin.welcomeScene().show();
                        Error.errorCount = 0;
                    } else if (usernameInputString.equals(user[1]) && !hashedPassword.equals(user[2])) {
                        Error.errorSound();
                        Message.bottomMessage("ERROR: Wrong password!", borderPane);
                        Error.errorCount++;
                        if (Error.errorCount == Properties.getMaxErrorWithoutGetBlocked()) {
                            Error.errorSound();
                            Message.bottomMessage("ERROR: Please wait for " + Properties.getBlockTime() + " seconds to make a new operation.", borderPane);
                            freeTimeText.setText(String.valueOf((System.currentTimeMillis() + (Properties.getBlockTime()* 1000L))));
                            Error.errorCount = 0;
                        }
                    } else if (usernameInputString.equals("")) {
                        Error.errorSound();
                        Message.bottomMessage("ERROR: Username can not be empty!", borderPane);
                        Error.errorCount++;
                        if (Error.errorCount == Properties.getMaxErrorWithoutGetBlocked()) {
                            Error.errorSound();
                            Message.bottomMessage("ERROR: Please wait for " + Properties.getBlockTime() + " seconds to make a new operation.", borderPane);
                            freeTimeText.setText(String.valueOf((System.currentTimeMillis() + (Properties.getBlockTime()* 1000L))));
                            Error.errorCount = 0;
                        }
                    } else if (passwordInput.getText().equals("")) {
                        Error.errorSound();
                        Message.bottomMessage("ERROR: Password can not be empty!", borderPane);
                        Error.errorCount++;
                        if (Error.errorCount == Properties.getMaxErrorWithoutGetBlocked()) {
                            Error.errorSound();
                            Message.bottomMessage("ERROR: Please wait for " + Properties.getBlockTime() + " seconds to make a new operation.", borderPane);
                            freeTimeText.setText(String.valueOf((System.currentTimeMillis() + (Properties.getBlockTime()* 1000L))));
                            Error.errorCount = 0;
                        }
                    } else if (!usernameList.contains(usernameInputString)) {
                        Error.errorSound();
                        Message.bottomMessage("ERROR: There is no such credentials.", borderPane);
                        Error.errorCount++;
                        if (Error.errorCount == Properties.getMaxErrorWithoutGetBlocked()) {
                            Error.errorSound();
                            Message.bottomMessage("ERROR: Please wait for " + Properties.getBlockTime() + " seconds to make a new operation.", borderPane);
                            freeTimeText.setText(String.valueOf((System.currentTimeMillis() + (Properties.getBlockTime()* 1000L))));
                            Error.errorCount = 0;
                        }
                    }
                }
            }
        });

        signUpButton.setOnAction(event -> {
            loginStage.close();
            SignUp.signUpScene().show();
        });

        Scene scene = new Scene(borderPane, 550, 320);
        loginStage.setTitle(Properties.getTitle());
        loginStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return loginStage;
    }

    private static String hashPassword (String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8) ;
        byte[] md5Digest = new byte[0];
        try{
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword) ;
        } catch (NoSuchAlgorithmException e ) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest) ;
    }

}
