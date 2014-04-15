package org.jgameengine.audio;

public interface SampleHandle {

    void playSample();

    void stopSample();

    void loopSample();

    void setVolume(int volume);

    void setVolume(float volume);
}
