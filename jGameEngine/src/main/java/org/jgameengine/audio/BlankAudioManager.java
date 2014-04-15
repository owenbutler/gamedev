package org.jgameengine.audio;

public class BlankAudioManager
        implements AudioManager {

    public void initAudio() {
    }

    public void shutdownAudio() {
    }

    public void updateAudio() {
    }

    private StreamHandle blankStreamHandle = new StreamHandle() {
        public void playStream() {
        }

        public void stopStream() {
        }
    };

    private SampleHandle blankSampleHandle = new SampleHandle() {
        public void playSample() {
        }

        public void stopSample() {
        }

        public void loopSample() {
        }

        public void setVolume(int volume) {
        }

        public void setVolume(float volume) {
        }
    };

    public StreamHandle loadMusicFromURI(String filename) {
        return blankStreamHandle;
    }

    public StreamHandle loadMusicInMemory(String filename) {
        return blankStreamHandle;
    }

    public StreamHandle loadMusicInMemoryFromClasspath(String filename) {
        return blankStreamHandle;
    }

    public void playMusic(SampleHandle music) {
    }

    public SampleHandle loadOrGetSample(String filename) {
        return blankSampleHandle;
    }

    public SampleHandle loadOrGetSample(String filename, int volume) {
        return blankSampleHandle;
    }

    public void playSample(SampleHandle sample) {
    }
}
