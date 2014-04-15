package org.jgameengine.renderer.lwjgl;

import static org.lwjgl.opengl.GL11.glBindTexture;

public class LWJGLCachingTextureBinder {

    private static int lastTextureId = -99999;

    public static void bindTexture(int mode, int textureId) {

        if (textureId != lastTextureId) {
            lastTextureId = textureId;
            glBindTexture(mode, textureId);
        }
    }

    public static void reset() {
        lastTextureId = -999999;
    }
}
