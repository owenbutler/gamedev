package org.jgameengine.renderer;

public interface Renderable {

    boolean enabled();

    void disable();

    void enable();

    void render();

    float getSortZ();
}
