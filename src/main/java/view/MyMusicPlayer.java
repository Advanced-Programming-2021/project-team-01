package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyMusicPlayer {
    private static MediaPlayer attackPlayer;
    private static MediaPlayer flipPlayer;
    private static MediaPlayer destroyMonster;
    private static MediaPlayer spawnPlayer;
    private static MediaPlayer setCard;
    private static MediaPlayer blip;

    static {
        Media media1 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/Blueeyesroar.mp3").toExternalForm());
        Media media2 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/flip.mp3").toExternalForm());
        Media media3 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/hologramspawn.mp3").toExternalForm());
        Media media4 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/Monsterdestroyed.mp3").toExternalForm());
        Media media5 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/setcard .mp3").toExternalForm());
        Media media6 = new Media(MyMusicPlayer.class.getResource("/Assets/sound/letsduel.mp3").toExternalForm());
        attackPlayer = new MediaPlayer(media1);
        flipPlayer = new MediaPlayer(media2);
        destroyMonster = new MediaPlayer(media4);
        spawnPlayer = new MediaPlayer(media3);
        setCard = new MediaPlayer(media5);
        blip = new MediaPlayer(media6);
    }

    static void attack(){
        if (!GameView.getInstance().isPlaying) return;
        attackPlayer.play();
        attackPlayer.setOnEndOfMedia(attackPlayer::stop);
    }

    static void spawn(){
        if (!GameView.getInstance().isPlaying) return;
        spawnPlayer.play();
        spawnPlayer.setOnEndOfMedia(spawnPlayer::stop);
    }

    static void flip(){
        if (!GameView.getInstance().isPlaying) return;
        flipPlayer.play();
        flipPlayer.setOnEndOfMedia(flipPlayer::stop);
    }

    static void destroyMonster(){
        if (!GameView.getInstance().isPlaying) return;
        destroyMonster.play();
        destroyMonster.setOnEndOfMedia(destroyMonster::stop);
    }

    static void setCard(){
        if (!GameView.getInstance().isPlaying) return;
        setCard.play();
        setCard.setOnEndOfMedia(setCard::stop);
    }

    static void blip(){
        if (!GameView.getInstance().isPlaying) return;
        blip.play();
        blip.setOnEndOfMedia(blip::stop);
    }
}
