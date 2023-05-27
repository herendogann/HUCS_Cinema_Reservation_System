import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Error {
    static int errorCount = 0;
    public static void errorSound() {
        MediaPlayer errorSoundPlayer = new MediaPlayer(new Media(new File("assets/effects/error.mp3").toURI().toString()));
        errorSoundPlayer.play();
    }
}
