package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * This class is for the handling of sound effects. Sound effects usually occur after certain events.
 */
public class SoundEffect {
    Clip clip;

    /**
     * For initialising the sound effect .wav file into the object.
     * @param filename the name of the sound effect .wav file to play
     */
    public void setFile(String filename) {
        try {
            File file = new File(filename);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            System.out.println("Error in setting up sound file.");
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
