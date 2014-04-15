package org.jgameengine.renderer;

public interface HudManager {

    void init();

    void renderHud();

    void registerHudCallback(HudCallback hudCallback);

    void deRegisterHudCallback(HudCallback hudCallback);
}
