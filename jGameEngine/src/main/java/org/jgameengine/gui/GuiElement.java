package org.jgameengine.gui;

import org.jgameengine.renderer.Renderable;

public interface GuiElement extends Renderable {

    void mouseOver();

    void mouseOut();

    void mouseClick();

    int getElementArea();
}
