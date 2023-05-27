import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Message {
    public static void topMessage(String message, BorderPane borderPane){
        VBox vBox = new VBox(5);
        new TextPane(message, vBox);
        borderPane.setTop(vBox);
    }
    public static void bottomMessage(String message, BorderPane borderPane){
        VBox vBox = new VBox(5);
        new TextPane(message, vBox);
        borderPane.setBottom(vBox);
    }
}
