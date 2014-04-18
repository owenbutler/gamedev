package org.jgameengine.audio.slickutil;

import org.jgameengine.audio.AudioManager;
import org.jgameengine.audio.SampleHandle;
import org.jgameengine.audio.StreamHandle;
import org.newdawn.slick.openal.SoundStore;

import java.util.Map;
import java.util.TreeMap;

public class SlickUtilAudioManager
        implements AudioManager {

    private Map<String, SlickUtilSampleHandle> samples = new TreeMap<>();
    private Map<String, SlickUtilStreamHandle> musics = new TreeMap<>();

    public void initAudio() {
    }

    public void shutdownAudio() {
    }

    public void updateAudio() {
        SoundStore.get().poll(0);
    }

    public StreamHandle loadMusicFromURI(String uri) {

        SlickUtilStreamHandle streamHandle = musics.get(uri);

        if (streamHandle == null) {
            streamHandle = new SlickUtilStreamHandle();
            streamHandle.loadStream(uri);
            musics.put(uri, streamHandle);
        }

        return streamHandle;
    }

    public StreamHandle loadMusicInMemory(String filename) {
        throw new RuntimeException("not implemented");
    }

    public StreamHandle loadMusicInMemoryFromClasspath(String filename) {
        throw new RuntimeException("not implemented");
    }

    public SampleHandle loadOrGetSample(String filename) {
        SlickUtilSampleHandle sample = samples.get(filename);
        if (sample == null) {
            sample = new SlickUtilSampleHandle();
            sample.loadSample(filename);
            samples.put(filename, sample);
        }

        return sample;
    }

    public SampleHandle loadOrGetSample(String filename, int volume) {
        SampleHandle sample = loadOrGetSample(filename);
        sample.setVolume(volume);

        return sample;
    }
}
