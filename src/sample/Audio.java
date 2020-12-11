package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Audio {
    private static final File[] files = new File[2];

    /** This method will play a fail sound
     *
     * @return MediaPlayer
     */
    public static MediaPlayer playLoseAudio(){
        files[0] = new File("file:audios/wrong.mp3");
        Media media = new Media(files[0].toURI().toString());
        return new MediaPlayer(media);
    }

    /** This method will play success sound
     *
     * @return MediaPlayer
     */
    public static MediaPlayer playWinAudio(){
        files[1] = new File("file:audios/correct.mp3");
        Media media = new Media(files[1].toURI().toString());
        return new MediaPlayer(media);
    }
}
