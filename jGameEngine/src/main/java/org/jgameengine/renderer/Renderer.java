package org.jgameengine.renderer;

import java.util.Set;

public interface Renderer {

    void init();

    void shutdown();

    void renderFrame();

    void finishFrame();

    void addRenderable(Renderable newRenderable);

    void removeRenderable(Renderable oldRenderable);

    void removeRenderables(Set oldRenderables);

    int getScreenWidth();

    int getScreenHeight();
}
