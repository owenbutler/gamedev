package org.jgameengine.renderer.animation;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class AnimationBufferCache {

    private static Map<String, FloatBuffer[]> frameArrayMap = new HashMap<String, FloatBuffer[]>();

    public static FloatBuffer[] getAnimationFrames(int tilesHorizontal, int tilesVertical) {

        String key = Integer.toString(tilesHorizontal) + "," + Integer.toString(tilesVertical);

        FloatBuffer[] frames = frameArrayMap.get(key);
        if (frames == null) {
            frames = getFrameArray(tilesHorizontal, tilesVertical);
            frameArrayMap.put(key, frames);
        }

        return frames;
    }

    public static FloatBuffer[] getFrameArray(int tilesHorizontal, int tilesVertical) {

        float[] originalArray = new float[]{
                1.0f, 1.0f,
                0.5f, 0.5f, 0.0f,
                0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f
        };

        int numTiles = tilesHorizontal * tilesVertical;

        FloatBuffer[] tiles = new FloatBuffer[numTiles];

        float tileWidth = 1.0f / (float) tilesHorizontal;
        float tileHeight = 1.0f / (float) tilesVertical;

        for (int i = 0; i < numTiles; ++i) {
            tiles[i] = BufferUtils.createFloatBuffer(20);

            float x1 = tileWidth + tileWidth * (float) (i % tilesHorizontal);
            originalArray[0] = x1;
            float y1 = tileHeight + (tileHeight * (i / tilesHorizontal));
            originalArray[1] = y1;

            float x2 = tileWidth * (float) (i % tilesHorizontal);
            originalArray[5] = x2;
            float y2 = tileHeight + (tileHeight * (i / tilesHorizontal));
            originalArray[6] = y2;

            float x3 = tileWidth + tileWidth * (float) (i % tilesHorizontal);
            originalArray[10] = x3;
            float y3 = tileHeight * ((i / tilesHorizontal));
            originalArray[11] = y3;

            float x4 = tileWidth * (float) (i % tilesHorizontal);
            originalArray[15] = x4;
            float y4 = tileHeight * ((i / tilesHorizontal));
            originalArray[16] = y4;

            tiles[i].put(originalArray).flip();
        }

        return tiles;
    }

    public static void main(String[] args) {

        int tilesHorizontal = 3, tilesVertical = 3;

        int num = tilesHorizontal * tilesVertical;

        float tileHeight = 1.0f / (float) tilesVertical;

        for (int i = 0; i < num; ++i) {

            float y3 = tileHeight * ((i / tilesHorizontal));

            System.out.println("y3 = " + y3);
        }
    }
}
