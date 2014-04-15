package org.jgameengine.renderer;

import org.jgameengine.renderer.lwjgl.LWJGLSurface2d;

public class Surface2dFactory {

    public static LWJGLSurface2d getSurface2d() {
        return new LWJGLSurface2d();
    }

}
