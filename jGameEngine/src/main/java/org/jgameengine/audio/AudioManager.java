package org.jgameengine.audio;

public interface AudioManager {

    void initAudio();

    void shutdownAudio();

    void updateAudio();

    StreamHandle loadMusicFromURI(String filename);

    StreamHandle loadMusicInMemory(String filename);

    StreamHandle loadMusicInMemoryFromClasspath(String filename);

    SampleHandle loadOrGetSample(String filename);

    SampleHandle loadOrGetSample(String filename, int volume);

}
