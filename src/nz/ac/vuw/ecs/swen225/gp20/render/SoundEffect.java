package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class is for the handling of sound effects. Sound effects usually occur after certain events.
 * Tutorial was followed to create this class. Link: https://www.youtube.com/watch?v=qPVkRtuf9CQ
 * @author John Eleigio Cecilio (cecilijohn - 300485264)
 */
public class SoundEffect {
    Clip clip;

    /**
     * For initialising the sound effect .wav file into the object.
     * @param filename the name of the sound effect .wav file to play
     */
    public void setFile(String filename) {
        File file = new File(filename);
        AudioInputStream sound = null;
        try {
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that plays the sound effect when called.
     */
    public void playSound() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
