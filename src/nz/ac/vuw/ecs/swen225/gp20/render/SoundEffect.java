package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {
    Clip clip;

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

    public void playSound() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
