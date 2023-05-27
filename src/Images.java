import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Images {
    public static ImageView getEmptySeat() {
        Image image = new Image(new File("assets/icons/empty_seat.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
    public static ImageView getReservedSeat() {
        Image image = new Image(new File("assets/icons/reserved_seat.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
}
