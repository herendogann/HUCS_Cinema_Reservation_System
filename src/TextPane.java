import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TextPane {
    public TextPane(String text, VBox textVBox){
        textVBox.setAlignment(Pos.CENTER);
        String[] textLines = text.split("\n");
        for (String textLine : textLines) {
            textVBox.getChildren().add(new Label(textLine));
        }
        textVBox.setPadding(new Insets(5));
    }
}
