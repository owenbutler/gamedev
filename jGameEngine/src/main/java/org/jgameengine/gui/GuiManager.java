package org.jgameengine.gui;

public interface GuiManager {

    void showGui();

    void hideGui();

    void registerGuiElement(GuiElement element);

    void deRegisterGuiElement(GuiElement element);

    void removeAllGuiElements();
}
