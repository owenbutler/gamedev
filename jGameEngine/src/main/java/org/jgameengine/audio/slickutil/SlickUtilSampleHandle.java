package org.jgameengine.audio.slickutil;

import org.apache.log4j.Logger;
import org.jgameengine.audio.SampleHandle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import java.io.IOException;

public class SlickUtilSampleHandle implements SampleHandle {

    private static final Logger logger = Logger.getLogger(SlickUtilSampleHandle.class);

    private Audio sample;

    private float volume;

    public void playSample() {
        if (sample == null) {
            return;
        }

        sample.playAsSoundEffect(1.0f, volume, false);
    }

    public void stopSample() {
        if (sample == null) {
            return;
        }

        sample.stop();
    }

    public void loopSample() {
        if (sample == null) {
            return;
        }

        sample.playAsSoundEffect(1.0f, volume, true);
    }

    public void setVolume(int intVolume) {
        volume = (float) intVolume / 255.0f;
    }

    public void setVolume(float inVolume) {
        volume = inVolume;
    }

    public void loadSample(String filename) {

        try {
            sample = AudioLoader.getAudio("OGG", SlickUtilSampleHandle.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            logger.error("error loading sound sample : " + filename);
            sample = null;
        }
    }
}
