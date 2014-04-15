package org.jgameengine.audio.slickutil;

import org.apache.log4j.Logger;
import org.jgameengine.audio.StreamHandle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import java.io.IOException;

public class SlickUtilStreamHandle
        implements StreamHandle {

    private static final Logger logger = Logger.getLogger(SlickUtilStreamHandle.class);

    private Audio sample;

    private float volume;

    public void playStream() {
        sample.playAsMusic(1.0f, 1.0f, true);
    }

    public void stopStream() {
        sample.stop();
    }

    public void loadStream(String filename) {
        loadStream(filename, 1.0f);
    }

    public void loadStream(String filename, float volume) {

        this.volume = volume;

        try {
            sample = AudioLoader.getAudio("OGG", SlickUtilSampleHandle.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            logger.error("error loading sound sample : " + filename);
            sample = null;
        }
    }
}
