package view;

import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class TView {

    public MediaView mediaView;
    MediaPlayer mediaPlayer;

    public void initialize() {
        Media media = new Media(getClass().getResource("/Assets/YuGiOhGx.mp4").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }

    public void back(MouseEvent mouseEvent) {
        mediaPlayer.stop();
        ViewSwitcher.switchTo(View.MAIN);
    }
}
