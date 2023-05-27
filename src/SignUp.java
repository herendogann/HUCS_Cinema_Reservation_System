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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;


public class SignUp extends Application {
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        signUpScene();
    }

    public static Stage signUpScene(){
        Stage signUpStage = new Stage();
        GridPane gridPane = new GridPane();
        ArrayList<String[]> backup = Films.backup;

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        TextField usernameField = new TextField();
        PasswordField password1Field = new PasswordField();
        PasswordField password2Field = new PasswordField();

        Button loginButton = new Button("LOGIN");
        Button signUpButton = new Button("SIGN UP");

        Text usernameInput = new Text();
        Text password1Input = new Text();
        Text password2Input = new Text();

        ArrayList<String[]> users = Users.users;

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);

        gridPane.add(new Label("Password:"), 0,1);
        gridPane.add(password1Field, 1,1);

        gridPane.add(new Label("Password:"), 0,2);
        gridPane.add(password2Field, 1,2);

        gridPane.add(loginButton, 0,3);
        gridPane.add(signUpButton,1,3);


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);

        Message.topMessage("Welcome to the HUCS Cinema Reservation System!\n" +
                "Fill the form below to create a new account.\n" +
                "You can create a new account by clicking SIGN UP button.", borderPane);

        signUpButton.setOnAction(event -> {
            usernameInput.setText(usernameField.getText());
            password1Input.setText(password1Field.getText());
            password2Input.setText(password2Field.getText());
            String usernameInputString = usernameInput.getText();
            String password1 = password1Input.getText();
            String password2 = password2Input.getText();

            ArrayList<String> usernameList = Users.getUsernameList();

                if (!usernameList.contains(usernameInputString) && password1.equals(password2) && !password1.equals(""))  {
                    String hashedPassword = hashPassword(password1);
                    Users.addUser(usernameInputString, hashedPassword, false, false);
                    usernameList.add(usernameInputString);
                    Message.bottomMessage("SUCCESS: You have successfully registered with your new credentials.", borderPane);
                } else if (usernameInputString.equals("")) {
                    Error.errorSound();
                    Message.bottomMessage("ERROR: Username can not be empty!", borderPane);
                } else if (password1.equals("") && password2.equals("")) {
                    Error.errorSound();
                    Message.bottomMessage("ERROR: Password can not be empty!", borderPane);
                } else if (!password1.equals(password2)){
                    Error.errorSound();
                    Message.bottomMessage("ERROR: Passwords do not match!", borderPane);
                } else if (usernameList.contains(usernameInputString)) {
                    Error.errorSound();
                    Message.bottomMessage("ERROR: This username already exists!", borderPane);
                }
        });

        loginButton.setOnAction(event -> {
            signUpStage.close();
            Login.loginScene().show();
        });

        Scene scene = new Scene(borderPane, 600,380);
        signUpStage.setTitle(Properties.getTitle());
        signUpStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
        signUpStage.setScene(scene);
        signUpStage.setResizable(false);
        signUpStage.setOnCloseRequest(event -> {
            Writer.deleteBackup();
            Writer.writeToBackup(backup);
        });
        return signUpStage;
    }
    private static String hashPassword ( String password ) {
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
